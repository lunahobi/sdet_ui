package helpers;

public enum Endpoint {
    MANAGER(PropertyProvider.getInstance().getProperty("web.url")),
    ADD_CUSTOMER(PropertyProvider.getInstance().getProperty("web.url").concat("/addCust")),
    CUSTOMERS(PropertyProvider.getInstance().getProperty("web.url").concat("/list"));

    private final String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

