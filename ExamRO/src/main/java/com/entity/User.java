package com.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class User  implements Serializable{
	private long id;
	private Date date;
}
