package me.ni3cz3k4j.metaEngine.resourcepack;

import java.io.IOException;

public interface MetaResourcePackContributor {
    void contribute(MetaResourcePackBuildContext context) throws IOException;
}