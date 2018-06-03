package com.example.dell.dncstdio;

public class Registerdata {
    String first_name;
    String last_name;
    String email_id;
    String mobile_number;
    String password;
    public Registerdata(){
    }
	public String getfirstName() {

		return first_name;
	}
    public void setfirstName(String first_name){
     this.first_name =first_name;
     }

	public String getlastName() {

		return last_name;
	}
	public void setlastName(String last_name)
    {
     this.last_name =last_name;
    }
	public String getEmailId() {

		return email_id;
	}
	public void setEmailId(String email_id){
     this.email_id =email_id;
 }
	public String getMobNo() {

		return  mobile_number;
	}
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setMobNo(String mobile_number){

        this.mobile_number=mobile_number;
    }
}
