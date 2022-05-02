package Attempt;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {
     
    public AuthFilter() {
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {    
        try {
 
            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();
            if ( reqURI.indexOf("/Login.xhtml") >= 0 || reqURI.indexOf("/Regist.xhtml") >= 0 || (ses != null && ses.getAttribute("email") != null)     //these links are accessible by URL without being logged
                                       || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource") ) {      //if anyone attempts to access a link that isn't here, it will be redirected to the login page
                
                if (reqURI.indexOf("/AdminProductsPage.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){  //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));    
                } else if(reqURI.indexOf("/UserProductsPage.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){  //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminProductsPage.xhtml");    // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminCreateProduct.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserCreateProduct.xhtml");  // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                } else if(reqURI.indexOf("/UserCreateProduct.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){ //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminCreateProduct.xhtml");   // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminUpdateProduct.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserUpdateProduct.xhtml");   // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                } else if(reqURI.indexOf("/UserUpdateProduct.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){ //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminUpdateProduct.xhtml");   // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminDeleteProduct.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserDeleteProduct.xhtml");   // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                } else if(reqURI.indexOf("/UserDeleteProduct.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){ //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminDeleteProduct.xhtml");   // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminFaturasPage.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){   //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserFaturasPage.xhtml"); // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                } else if(reqURI.indexOf("/UserFaturasPage.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){   //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminFaturasPage.xhtml"); // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminMyProducts.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){    //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserMyProducts.xhtml");  // is redirected to the respective user page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                } else if(reqURI.indexOf("/UserMyProducts.xhtml") >= 0 && ses != null && ses.getAttribute("email").equals("admin")){    //if an admin tries to access an user page
                    res.sendRedirect(req.getContextPath() + "/AdminMyProducts.xhtml");  // is redirected to the respective admin page
                }
                
                if (reqURI.indexOf("/AdminCreateCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){    //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                if (reqURI.indexOf("/AdminUpdateCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){    //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                if (reqURI.indexOf("/AdminDeleteCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){    //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                if (reqURI.indexOf("/AdminCreateSubCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                if (reqURI.indexOf("/AdminUpdateSubCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                if (reqURI.indexOf("/AdminDeleteSubCategory.xhtml") >= 0 && ses != null && !ses.getAttribute("email").equals("admin")){ //if an user tries to access an admin page
                     res.sendRedirect(req.getContextPath() + "/UserProductsPage.xhtml");    // is redirected to this page because normal users don't have access to the requested page
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciais inválidas."));
                }
                
                
                chain.doFilter(request, response);
            } else {   // user didn't log in but asking for a page that is not allowed so take user to login page
                   res.sendRedirect(req.getContextPath() + "/Login.xhtml");  // Anonymous user. Redirect to login page
            }
        }
        catch(Throwable t) {
            System.out.println( t.getMessage());
        }
    } //doFilter
 
    @Override
    public void destroy() {
         
    }
}