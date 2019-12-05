package com.ccc.common.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class GobalValidator extends Validator {

	@Override
	protected void validate(Controller c) {

		setShortCircuit(true);

		switch (getActionKey()) {
		
		case "/verisign":
			validateRequiredString("check", "TipMsg", "请勾选用户注册协议");
			validateEmail("email", "TipMsg", "邮箱格式不正确");
			validateRequiredString("nickname", "TipMsg", "请输入昵称");
			validateRequiredString("pass1", "TipMsg", "请输入密码");
			validateRequiredString("pass2", "TipMsg", "请再次输入密码");
			validateString("pass1", 6, 18, "TipMsg", "密码最低6位数！");
			validateString("pass2", 6, 18, "TipMsg", "密码最低6位数！");
			validateEqualField("pass1", "pass2", "TipMsg", "两次密码不相同，请重新输入!");
			break;

		case "/tologin":
			validateEmail("email", "TipMsg", "邮箱格式不正确");
			validateRequiredString("email", "TipMsg", "请输入邮箱");
			validateRequiredString("pass", "TipMsg", "请输入密码");
			break;

		case "/veriforgetpass":
			validateEmail("email", "TipMsg", "邮箱格式不正确");
			break;
			
		case "/tomodifypass":
			validateEmail("email", "TipMsg", "邮箱格式不正确");
			validateRequiredString("pass1", "TipMsg", "请输入密码");
			validateRequiredString("pass2", "TipMsg", "请再次输入密码");
			validateString("pass1", 6, 18, "TipMsg", "密码最低6位数！");
			validateString("pass2", 6, 18, "TipMsg", "密码最低6位数！");
			validateEqualField("pass1", "pass2", "TipMsg", "两次密码不相同，请重新输入!");
			break;
		
		case "/addpost":
			validateRequiredString("title", "TipMsg", "请填写标题");
			validateRequiredString("message", "TipMsg", "请填写内容");
			break;

		default:
			break;
		}

	}

	@Override
	protected void handleError(Controller c) {

		switch (getActionKey()) {

		case "/verisign":
			c.forwardAction("/sign");			
			break;

		case "/tologin":
			c.forwardAction("/login");
			break;
			
		case "/veriforgetpass":
			c.forwardAction("/forgetpass");
			break;
			
		case "/tomodifypass":
			c.forwardAction("/modifypass");
			break;
			
		case "addpost":
			c.forwardAction("/user");
			break;

		default:
			break;
		}

	}

}
