package ru.dreamcloud.pharmatracker.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;

/*delimiter $$

CREATE TABLE `documents` (
  `document_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT NULL,
  `date_modified` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `extension` int(11) DEFAULT NULL,
  `posted_by_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `fk_extension_document` (`extension`),
  KEY `fk_event_document` (`event`),
  KEY `fk_postedbyuser_document_idx` (`posted_by_user`),
  CONSTRAINT `fk_postedbyuser_document` FOREIGN KEY (`posted_by_user`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_document` FOREIGN KEY (`event`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_extension_document` FOREIGN KEY (`extension`) REFERENCES `extensions` (`extension_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
 */

@Entity
@Table(name="documents")
public class Document implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="document_id")
	private Integer documentId;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "extension", referencedColumnName = "extension_id")
	private Extension extension;
	
	private String title;
	
	private String description;
	
	@Column(name="file_url")
	private String fileURL;
	
	@Column(name="date_created")
	private Timestamp dateCreated;
	
	@Column(name="date_modified")
	private Timestamp dateModified;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "event", referencedColumnName = "event_id")
	private Event event;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "posted_by_user")
	private CommonUserInfo postedByUser;
	
	public Document() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}	

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getDateModified() {
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public CommonUserInfo getPostedByUser() {
		return postedByUser;
	}

	public void setPostedByUser(CommonUserInfo postedByUser) {
		this.postedByUser = postedByUser;
	}	

}
