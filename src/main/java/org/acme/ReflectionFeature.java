package org.acme;

import com.azure.storage.file.share.models.ShareStorageException;
import io.netty.handler.ssl.OpenSsl;
import java.lang.reflect.Modifier;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;
import org.graalvm.nativeimage.hosted.RuntimeReflection;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

@Slf4j
public class ReflectionFeature implements Feature {
   @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
      RuntimeClassInitialization.initializeAtRunTime("com.azure.core.http.vertx.VertxAsyncHttpClientProvider$GlobalVertxHttpClient");
      RuntimeClassInitialization.initializeAtRunTime("com.azure.core.http.vertx.VertxAsyncHttpClientBuilder$DefaultVertx");
      RuntimeClassInitialization.initializeAtRunTime("io.netty.internal.tcnative.SSL");
      RuntimeClassInitialization.initializeAtRunTime(OpenSsl.class.getName());
      RuntimeClassInitialization.initializeAtRunTime("io.netty.util.concurrent.GlobalEventExecutor");
      registerClassForReflection(ShareStorageException.class);
      registerPackage("com.azure.storage.file.share.implementation.models");
      registerPackage("com.azure.storage.file.share.models");
    }

    @Override
    public String getDescription() {
        return "Register annotated types for reflection";
    }

     private static void registerPackage(String prefix) {
        new Reflections(prefix, Scanners.SubTypes.filterResultsBy(s -> true))
                .getSubTypesOf(Object.class)
                .forEach(ReflectionFeature::registerClassForReflection);
    }

    private static void registerClassForReflection(Class<?> clazz) {
      log.info("Registering azure fileshare class for reflection: {}", clazz.getName());
      RuntimeReflection.register(clazz);
      RuntimeReflection.register(clazz.getDeclaredConstructors());
      RuntimeReflection.register(clazz.getDeclaredFields());
      RuntimeReflection.register(clazz.getDeclaredMethods());
      RuntimeReflection.registerAllDeclaredConstructors(clazz);

      // also register declared classes like builders
      for (Class<?> nestedClass : clazz.getDeclaredClasses()) {
          if (!Modifier.isPrivate(nestedClass.getModifiers())) {
              registerClassForReflection(nestedClass);
          }
      }
  }
  }
