/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.web.api.services;

import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.alfresco.repo.template.AbsoluteUrlMethod;
import org.alfresco.repo.template.ISO8601DateFormatMethod;
import org.alfresco.repo.template.UrlEncodeMethod;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.TemplateProcessor;
import org.alfresco.service.descriptor.DescriptorService;
import org.alfresco.web.api.APIContextAware;
import org.alfresco.web.api.APIRequest;
import org.alfresco.web.api.APIResponse;
import org.alfresco.web.api.APIService;
import org.alfresco.web.api.FormatRegistry;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Skeleton implementation of an API Service
 *
 * @author davidc
 */
public abstract class APIServiceImpl implements BeanNameAware, APIService, APIContextAware 
{
    private String name;
    private String uri;

    // dependencies
    private ServletContext context;
    private ServiceRegistry serviceRegistry;
    private DescriptorService descriptorService;
    private TemplateProcessor templateProcessor;
    private FormatRegistry formatRegistry;

    //
    // Initialisation
    //
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.api.APIContextAware#setAPIContext(javax.servlet.ServletContext)
     */
    public void setAPIContext(ServletContext context)
    {
        this.context = context;
    }
    
    /**
     * @param serviceRegistry 
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * @param templateProcessor
     */
    public void setTemplateProcessor(TemplateProcessor templateProcessor)
    {
        this.templateProcessor = templateProcessor;
    }

    /**
     * @param descriptorService
     */
    public void setDescriptorService(DescriptorService descriptorService)
    {
        this.descriptorService = descriptorService;
    }
    
    /**
     * @param formatRegistry
     */
    public void setFormatRegistry(FormatRegistry formatRegistry)
    {
        this.formatRegistry = formatRegistry;
    }
    
    /**
     * Sets the Http URI
     * 
     * @param uri
     */
    public void setHttpUri(String uri)
    {
        this.uri = uri;
    }
    
    //
    // Service Meta-Data
    //
    
    /* (non-Javadoc)
     * @see org.alfresco.web.api.APIService#getName()
     */
    public String getName()
    {
        return this.name;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.api.APIService#getHttpUri()
     */
    public String getHttpUri()
    {
        return this.uri;
    }

    
    //
    // Service Implementation Helpers
    //

    /**
     * @return descriptorService
     */
    protected ServletContext getAPIContext()
    {
        return context;
    }
    
    /**
     * @return descriptorService
     */
    protected DescriptorService getDescriptorService()
    {
        return descriptorService;
    }

    /**
     * @return serviceRegistry
     */
    protected ServiceRegistry getServiceRegistry()
    {
        return serviceRegistry;
    }
    
    /**
     * @return templateProcessor
     */
    protected TemplateProcessor getTemplateProcessor()
    {
        return templateProcessor;
    }
    
    /**
     * @return formatRegistry
     */
    protected FormatRegistry getFormatRegistry()
    {
        return formatRegistry;
    }
    
    
    //
    // Basic Templating Support
    //
    
    
    /**
     * Create a basic API model
     * 
     * @param req  api request
     * @param res  api response
     * @return  template model
     */
    protected Map<String, Object> createAPIModel(APIRequest req, APIResponse res)
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
        model.put("xmldate", new ISO8601DateFormatMethod());
        model.put("absurl", new AbsoluteUrlMethod(req.getPath()));
        model.put("urlencode", new UrlEncodeMethod());
        model.put("date", new Date());
        model.put("agent", descriptorService.getServerDescriptor());
        model.put("request", req);
        model.put("response", res);
        return model;
    }

    /**
     * Render a template (identified by path)
     * 
     * @param templatePath  template path
     * @param model  model
     * @param writer  output writer
     */
    protected void renderTemplate(String templatePath, Map<String, Object> model, Writer writer)
    {
        templateProcessor.process(templatePath, model, writer);
    }
    
    /**
     * Render a template (contents as string)
     * @param template  the template
     * @param model  model
     * @param writer  output writer
     */
    protected void renderString(String template, Map<String, Object> model, Writer writer)
    {
        templateProcessor.processString(template, model, writer);
    }
    

    /**
     * Helper to retrieve API Service
     * 
     * @param name  name of service
     * @return  the service
     */
    protected static APIService getMethod(String name)
    {
        String[] CONFIG_LOCATIONS = new String[] { "classpath:alfresco/application-context.xml", "classpath:alfresco/web-api-application-context.xml" };
        ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
        APIService method = (APIService)context.getBean(name);
        return method;
    }
    
    /**
     * Create a base test model (for use stand-alone)
     * 
     * @return  test model
     */
    protected Map<String, Object> createTestModel()
    {
        Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);

        // create api methods
        model.put("xmldate", new ISO8601DateFormatMethod());
        model.put("urlencode", new UrlEncodeMethod());
        model.put("absurl", new AbsoluteUrlMethod("http://test:8080/test"));
        model.put("date", new Date());
        
        // create dummy request model
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("servicePath", "http://localhost:8080/alfresco/service");
        request.put("path", "http://localhost:8080/alfresco");
        request.put("url", "http://localhost:8080/alfresco/service/testurl");
        request.put("guest", false);
        request.put("format", "xml");
        model.put("request", request);        

        // create dummy agent model
        model.put("agent", getDescriptorService().getServerDescriptor());
        
        return model;
    }
 
}
