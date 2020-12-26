package sample.model;

public class LogPacket {
    private String id;
    private String account;
    private String request;
    private String tableChange;
    private int idChange;

    public LogPacket(String account, String request, String tableChange, int idChange) {
        this.account = account;
        this.request = request;
        this.tableChange = tableChange;
        this.idChange = idChange;
    }

    public LogPacket(String id, String account, String request, String tableChange, int idChange) {
        this.id = id;
        this.account = account;
        this.request = request;
        this.tableChange = tableChange;
        this.idChange = idChange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public String getRequest() {
        return request;
    }

    public String getTableChange() {
        return tableChange;
    }

    public int getIdChange() {
        return idChange;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setTableChange(String tableChange) {
        this.tableChange = tableChange;
    }

    public void setIdChange(int idChange) {
        this.idChange = idChange;
    }
}
