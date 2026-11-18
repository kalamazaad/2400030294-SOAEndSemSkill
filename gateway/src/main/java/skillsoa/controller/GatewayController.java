package skillsoa.controller;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skillsoa.service.GatewayService;

@RestController
@RequestMapping("/")
public class GatewayController {

		  GatewayService GS;
		  
		  public GatewayController(GatewayService GS)
		  {
		    this.GS = GS;
		  }
		  
		  @PostMapping("{service}/{endpoint}")
		  public Object getService(@PathVariable String service, 
		               @PathVariable String endpoint,
		               @RequestBody Map<String, Object> data)
		  {
		    return GS.invokeInstance(HttpMethod.POST, service, endpoint, null, data);
		  }
		  
		  @GetMapping("{service}/{endpoint}")
		  public Object getService(@PathVariable String service,
		               @PathVariable String endpoint)
		  {
		    return GS.invokeInstance(HttpMethod.GET, service, endpoint, null, null);
		  }
		  
		  @GetMapping("{service}/{endpoint}/{id}") 
		  public Object getService(@PathVariable String service, 
		         @PathVariable String endpoint, 
		         @PathVariable Long id) 
		  { 
		   return GS.invokeInstance(HttpMethod.GET, service, endpoint + "/" + id, null, null); 
		  } 
		   
		  @PutMapping("{service}/{endpoint}/{id}") 
		  public Object putService(@PathVariable String service, 
		         @PathVariable String endpoint, 
		         @PathVariable Long id, 
		         @RequestBody Map<String, Object> data) 
		  { 
		   return GS.invokeInstance(HttpMethod.PUT, service, endpoint + "/" + id, null, data); 
		  } 
		  
		
		 @DeleteMapping("{service}/{endpoint}/{id}")
		 public Object getDeleteService(@PathVariable String service,
		 @PathVariable String endpoint,
		 @PathVariable Long id)
		 {
			 return GS.invokeInstance(HttpMethod.DELETE, service, endpoint + "/" + id, null, null);
		 }
}