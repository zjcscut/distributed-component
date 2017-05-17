package org.throwable.lock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/18 2:10
 */
@Data
@NoArgsConstructor
public class User {



	private Long id;
	private String name;
	private String account;
	private Integer age;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", account='" + account + '\'' +
				", age=" + age +
				'}';
	}
}
