package com.jotunheim.thor.domain;

public class ClientAccessKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column client_access_key.ID
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column client_access_key.APP_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    private String appKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column client_access_key.SECRET_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    private String secretKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column client_access_key.VERSION
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column client_access_key.ID
     *
     * @return the value of client_access_key.ID
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column client_access_key.ID
     *
     * @param id the value for client_access_key.ID
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column client_access_key.APP_KEY
     *
     * @return the value of client_access_key.APP_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column client_access_key.APP_KEY
     *
     * @param appKey the value for client_access_key.APP_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column client_access_key.SECRET_KEY
     *
     * @return the value of client_access_key.SECRET_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column client_access_key.SECRET_KEY
     *
     * @param secretKey the value for client_access_key.SECRET_KEY
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column client_access_key.VERSION
     *
     * @return the value of client_access_key.VERSION
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column client_access_key.VERSION
     *
     * @param version the value for client_access_key.VERSION
     *
     * @mbggenerated Thu Oct 20 19:39:59 CST 2016
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}