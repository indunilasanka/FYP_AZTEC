package aztec.rbir_database.Entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "request")
public class Request {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
	int requestId;
	
	@Column(name= "request")
	String request;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "public_user", nullable = false)
	PublicUser pUser;
	
	@Column
	@CreationTimestamp
	private Timestamp createDateTime;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public PublicUser getpUser() {
		return pUser;
	}

	public void setpUser(PublicUser pUser) {
		this.pUser = pUser;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
	
	
}
