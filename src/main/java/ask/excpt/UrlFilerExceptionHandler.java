package ask.excpt;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * MVC based exception
 *
 * @author Abid Khan
 */
@ControllerAdvice
public class UrlFilerExceptionHandler {

    @ExceptionHandler(UrlManagerException.class)
    public ModelAndView handleException(UrlManagerException exception, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
