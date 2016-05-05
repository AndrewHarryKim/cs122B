import java.sql.Date;

public class Star {
/*
id:integer (primary key)
first_name:varchar(50) 
last_name:varchar(50) 
dob:date 
photo_url:varchar(200) 
 */
	
	
	private int id=-1;

	private String first_name;

	private String last_name;
	
	private Date dob;
	
	private String photo_url;
	
	public Star(){
		
	}
	
	public Star(int id, String first_name, String last_name,Date dob, String photo_url) {
		
		this.id=id;
		this.first_name=first_name;
		this.last_name=last_name;
		this.dob=dob;
		this.photo_url=photo_url;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Star Details - ");
		sb.append("Id:" + getId());
		sb.append(", ");
		sb.append("Name:");
		if(getFirst_name()!=null)
			sb.append(getFirst_name());
		if(getLast_name()!=null)
			sb.append(" "+getLast_name());
		sb.append(", ");
		sb.append("Date of Birth:" + getDob());
		sb.append(", ");
		sb.append("Photo:" + getPhoto_url());
		sb.append(".");
		
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
}