package kubeapi.model;

import java.util.Objects;

public class ClusterData {

    private  String namespace;

    private String pod;

    private int numLogLines;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public int getNumLogLines() {
        return numLogLines;
    }

    public void setNumLogLines(int numLogLines) {
        this.numLogLines = numLogLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterData that = (ClusterData) o;
        return Objects.equals(namespace, that.namespace) &&
                Objects.equals(pod, that.pod);
    }

    @Override
    public int hashCode() {

        return Objects.hash(namespace, pod);
    }
}
