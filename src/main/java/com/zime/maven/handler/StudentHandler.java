package com.zime.maven.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zime.maven.entity.Student;
import com.zime.maven.service.StudentService;
import com.zime.maven.util.Msg;

@Controller
public class StudentHandler {
	@Autowired
	private StudentService studentService;
	
	
	@ResponseBody
	@RequestMapping(value="/stu/{ids}",method=RequestMethod.DELETE)
	public Msg delete(@PathVariable("ids")String ids) {
		if(ids.contains(",")) {
			String[] strIds=ids.split(",");
			List<Integer> stuIds=new ArrayList<Integer>();
			for(String s:strIds) {
				stuIds.add(Integer.parseInt(s));
			}
			studentService.deleteStudentBatch(stuIds);
		}else {
			Integer id=Integer.parseInt(ids);
			studentService.deleteStudent(id);
		}
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping(value="/stu/{id}",method=RequestMethod.GET)
	public Msg getStudent(@PathVariable("id")Integer id) {
		Student student=studentService.getStudent(id);
		return Msg.success().add("student",student);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/checkName")
	public Msg checkName(@RequestParam("stuName") String name) {
		String regxName=("(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,8}$)");
		if(!name.matches(regxName)) {
			return Msg.fail().add("va_name","姓名只能是2-8位汉字或者2-16位字符");
		}
		boolean flag=studentService.validateName(name);
		if(flag) {
			return Msg.success().add("va_name","姓名可用");
		}
		else {
			return Msg.fail().add("va_name","姓名不可用");
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/stu/{id}", method=RequestMethod.PUT)
	public Msg updateStudent(@Valid Student student,BindingResult result) {
		
		if(result.getErrorCount()>0) {
			Map<String,Object> errors=new HashMap<String, Object>();
			for(FieldError error:result.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
				errors.put(error.getField(),error.getDefaultMessage());
			}
			return Msg.fail().add("errors",errors);
		}
		try {
			studentService.updateStudent(student);
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail().add("errors",e.getMessage());
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/stu", method=RequestMethod.POST)
	public Msg save(@Valid Student student,BindingResult result) {
		
		if(result.getErrorCount()>0) {
			Map<String,Object> errors=new HashMap<String, Object>();
			for(FieldError error:result.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
				errors.put(error.getField(),error.getDefaultMessage());
			}
			return Msg.fail().add("errors",errors);
		}
		try {
			studentService.saveStudent(student);
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail().add("errors",e.getMessage());
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	//添加这句话后就可以返回Jackson格式的数据
	@ResponseBody
	@RequestMapping("/students")
	public Msg getStudentWithJSON(@RequestParam(value="pn",defaultValue="1") Integer pageNum) {
		//在查询之前调用静态方法设置起始页和页面大小
		PageHelper.startPage(pageNum, 8);
		//startPage后面紧跟着的查询就是分页查询
		List<Student> students=studentService.getAll();
		//使用PageInfo包装查询后的结果，并将pageInfo存入map中
		PageInfo<Student> pageInfo=new PageInfo<Student>(students,5);

		return Msg.success().add("page",pageInfo);
	}
	
	//@RequestMapping("/students")
	public String list(@RequestParam(value="pn",defaultValue="1") Integer pageNum,Map<String,Object> map) {
		//在查询之前调用静态方法设置起始页和页面大小
		PageHelper.startPage(pageNum, 8);
		//startPage后面紧跟着的查询就是分页查询
		List<Student> students=studentService.getAll();
		//使用PageInfo包装查询后的结果，并将pageInfo存入map中
		PageInfo<Student> pageInfo=new PageInfo<Student>(students,5);
		
		map.put("page", pageInfo);
		return "list";
	}
}
