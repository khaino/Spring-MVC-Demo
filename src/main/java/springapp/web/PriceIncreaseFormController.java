package springapp.web;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import springapp.service.PriceIncrease;
import springapp.service.PriceIncreaseValidator;
import springapp.service.ProductManager;

@Controller
public class PriceIncreaseFormController{

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ProductManager productManager;
    @Autowired
    private PriceIncreaseValidator priceIncreaseValidator;

    @RequestMapping(value="/submitprice.htm", method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("priceIncrease") PriceIncrease priceIncrease,BindingResult result)
            throws ServletException {
    	
    	priceIncreaseValidator.validate(priceIncrease, result);   	
    	
    	if (result.hasErrors()) {
    		return new ModelAndView("priceincrease");			 
		}
    	
        int increase = priceIncrease.getPercentage();
        logger.info("Increasing prices by " + increase + "%.");

        productManager.increasePrice(increase);

        return new ModelAndView(new RedirectView("hello.htm"));
    }
    
    @RequestMapping(value="/priceincrease.htm", method = RequestMethod.GET)
	public String helloWorld(Model model) {
    	        
         model.addAttribute("priceIncrease", new PriceIncrease());
         return "priceincrease";

    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

}