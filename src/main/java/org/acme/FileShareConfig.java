package org.acme;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "file-share")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface FileShareConfig {

  public String name();

  public String connectionstring();

}
