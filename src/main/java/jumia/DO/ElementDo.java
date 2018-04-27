package jumia.DO;

/**
 * @program: myselenium
 * @description: 页面元素对象
 * @author: lwx
 * @create: 2018-04-25 17:19
 **/

public class ElementDo {
    /**
     * 加载的url
     */
    private String loadUrl;
    /**
     * 商品信息
     */
    private String productInfo;
    /**
     * 查找商品搜索框
     */
    private String searchBox;
    /**
     * 搜索按钮
     */
    private String serachButton;
    /**
     * sku详情标签
     */
    private String skuInfoLink;
    /**
     * 五星评价
     */
    private String fiveStar;
    /**
     * 提交评价按钮
     */
    private String commitReviewButtion;

    /**
     * 登录账号框
     */
    private String loginAccountBox;

    /**
     * 登录密码框
     */
    private String loginPasswordBox;

    /**
     * 登录提交按钮
     */
    private String loginButton;

    public ElementDo() {
    }

    public ElementDo(String loadUrl, String productInfo, String searchBox, String serachButton, String skuInfoLink, String fiveStar, String commitReviewButtion, String loginAccountBox, String loginPasswordBox, String loginButton) {
        this.loadUrl = loadUrl;
        this.productInfo = productInfo;
        this.searchBox = searchBox;
        this.serachButton = serachButton;
        this.skuInfoLink = skuInfoLink;
        this.fiveStar = fiveStar;
        this.commitReviewButtion = commitReviewButtion;
        this.loginAccountBox = loginAccountBox;
        this.loginPasswordBox = loginPasswordBox;
        this.loginButton = loginButton;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getSearchBox() {
        return searchBox;
    }

    public void setSearchBox(String searchBox) {
        this.searchBox = searchBox;
    }

    public String getSerachButton() {
        return serachButton;
    }

    public void setSerachButton(String serachButton) {
        this.serachButton = serachButton;
    }

    public String getSkuInfoLink() {
        return skuInfoLink;
    }

    public void setSkuInfoLink(String skuInfoLink) {
        this.skuInfoLink = skuInfoLink;
    }

    public String getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(String fiveStar) {
        this.fiveStar = fiveStar;
    }

    public String getCommitReviewButtion() {
        return commitReviewButtion;
    }

    public void setCommitReviewButtion(String commitReviewButtion) {
        this.commitReviewButtion = commitReviewButtion;
    }

    public String getLoginAccountBox() {
        return loginAccountBox;
    }

    public void setLoginAccountBox(String loginAccountBox) {
        this.loginAccountBox = loginAccountBox;
    }

    public String getLoginPasswordBox() {
        return loginPasswordBox;
    }

    public void setLoginPasswordBox(String loginPasswordBox) {
        this.loginPasswordBox = loginPasswordBox;
    }

    public String getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(String loginButton) {
        this.loginButton = loginButton;
    }

    @Override
    public String toString() {
        return "ElementDo{" +
                "loadUrl='" + loadUrl + '\'' +
                ", searchBox='" + searchBox + '\'' +
                ", serachButton='" + serachButton + '\'' +
                ", skuInfoLink='" + skuInfoLink + '\'' +
                ", fiveStar='" + fiveStar + '\'' +
                ", commitReviewButtion='" + commitReviewButtion + '\'' +
                '}';
    }
}
