package it.dockins.dockerslaves;

import hudson.Extension;
import hudson.model.Job;
import it.dockins.dockerslaves.spi.DockerHostConfig;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.docker.commons.credentials.DockerServerEndpoint;
import org.kohsuke.stapler.DataBoundConstructor;
import it.dockins.dockerslaves.spi.DockerHostSource;
import it.dockins.dockerslaves.spi.DockerHostSourceDescriptor;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class DefaultDockerHostSource extends DockerHostSource {

    private final DockerServerEndpoint dockerServerEndpoint;
    public static final DockerServerEndpoint DEFAULT = new DockerServerEndpoint(null, null);

    public DefaultDockerHostSource() {
        this(new DockerServerEndpoint(null, null));
    }

    @DataBoundConstructor
    public DefaultDockerHostSource(DockerServerEndpoint dockerServerEndpoint) {
        this.dockerServerEndpoint = dockerServerEndpoint;
    }

    public DockerServerEndpoint getDockerServerEndpoint() {

        return dockerServerEndpoint != null ? dockerServerEndpoint : DEFAULT;
    }

    @Override
    public DockerHostConfig getDockerHost(Job job) throws IOException, InterruptedException {
        return new DockerHostConfig(getDockerServerEndpoint(), job);
    }

    @Extension
    public static class DescriptorImpl extends DockerHostSourceDescriptor {

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Standalone Docker daemon / Docker Swarm cluster";
        }
    }
}
