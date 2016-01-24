/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class InvalidEntity implements Serializable {
	
	@Entity
	public static class InnerClass {
		
	}
	
	@Id
	private Long id;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
