package com.tutorial.couchbase.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable {

	private final String code;
}