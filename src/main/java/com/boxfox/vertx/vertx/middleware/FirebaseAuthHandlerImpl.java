package com.boxfox.vertx.vertx.middleware;

import com.boxfox.vertx.util.LogUtil;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthHandlerImpl implements FirebaseAuthHandler {

    public FirebaseAuthHandlerImpl(String firebaseKeystore){
        try {
            System.out.println("Initilize firebase");
            FileInputStream serviceAccount = new FileInputStream(firebaseKeystore);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handle(RoutingContext ctx) {
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            FirebaseToken decodedToken = null;
            try {
                decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(token).get();
                if (decodedToken != null) {
                    String email = decodedToken.getEmail();
                    String name = decodedToken.getName();
                    String picture = decodedToken.getPicture();
                    String uid = decodedToken.getUid();
                    FirebaseUser user = new FirebaseUser(uid, email, name, picture);
                    ctx.setUser(user);
                    ctx.data().put("uid", uid);
                    ctx.next();
                    return;
                }
            } catch (InterruptedException | ExecutionException e) {
                LogUtil.getLogger().debug(e.getMessage());
            }
        }
        ctx.fail(HttpResponseStatus.UNAUTHORIZED.code());
    }

    public static class FirebaseUser extends AbstractUser {
        private String uid, email, name, picture;

        protected FirebaseUser(String uid, String email, String name, String picture){
            this.uid = uid;
            this.email = email;
            this.name = name;
            this.picture = picture;
        }

        @Override
        protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> resultHandler) {
            resultHandler.handle(Future.succeededFuture());
        }

        @Override
        public JsonObject principal() {
            JsonObject object = new JsonObject();
            object.put("uid", uid);
            object.put("email", email);
            object.put("name", name);
            object.put("picture", picture);
            return object;
        }

        @Override
        public void setAuthProvider(AuthProvider authProvider) {

        }
    }

}
