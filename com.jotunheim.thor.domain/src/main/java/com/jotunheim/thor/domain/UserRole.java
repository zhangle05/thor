package com.jotunheim.thor.domain;

public class UserRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.ID
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.ROLE_NAME
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.DESCRIPTION
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.RANK
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    private Integer rank;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.ID
     *
     * @return the value of user_role.ID
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.ID
     *
     * @param id the value for user_role.ID
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.ROLE_NAME
     *
     * @return the value of user_role.ROLE_NAME
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.ROLE_NAME
     *
     * @param roleName the value for user_role.ROLE_NAME
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.DESCRIPTION
     *
     * @return the value of user_role.DESCRIPTION
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.DESCRIPTION
     *
     * @param description the value for user_role.DESCRIPTION
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.RANK
     *
     * @return the value of user_role.RANK
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.RANK
     *
     * @param rank the value for user_role.RANK
     *
     * @mbggenerated Thu Oct 20 18:45:50 CST 2016
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }
}