package com.pattern.database.tables;


public class Users {

  private int idUser;
  private String email;
  private String login;
  private String pass;

  public Users(int idUser, String email, String login, String pass) {
    this.idUser = idUser;
    this.email = email;
    this.login = login;
    this.pass = pass;
  }

  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }


  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

}
