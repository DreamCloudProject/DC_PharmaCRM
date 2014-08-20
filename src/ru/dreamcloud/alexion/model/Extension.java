package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the extensions database table.
 * 
 */
@Entity
@Table(name="extensions")
public class Extension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="extension_id")
	private int extensionId;

	private String description;

	@Column(name="extension_name")
	private String extensionName;

	@Column(name="icon_name")
	private String iconName;

	private String title;

	public Extension() {
	}

	public int getExtensionId() {
		return this.extensionId;
	}

	public void setExtensionId(int extensionId) {
		this.extensionId = extensionId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtensionName() {
		return this.extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}