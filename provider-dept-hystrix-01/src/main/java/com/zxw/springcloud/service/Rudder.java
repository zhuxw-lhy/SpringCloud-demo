package com.cludove.dirigible.bridge;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cludove.dirigible.element.Resource;
import com.cludove.dirigible.element.control.Direct;
import com.cludove.dirigible.element.control.Logon;
import com.cludove.dirigible.element.entity.Buddle;
import com.cludove.dirigible.element.entity.Catalog;
import com.cludove.dirigible.element.entity.Journal;
import com.cludove.dirigible.gear.IOBase;
import com.cludove.dirigible.gear.exception.BaseException;
import com.cludove.dirigible.gear.file.DailyLogger;
import com.cludove.dirigible.gear.file.ErrorManager;
import com.cludove.dirigible.gear.file.properties.Parameter;
import com.cludove.dirigible.hold.distribution.DispersedBasis;
import com.cludove.dirigible.instrument.DataBase;
import com.cludove.dirigible.instrument.constant.Archives;
import com.cludove.dirigible.instrument.constant.Separator;
import com.cludove.dirigible.instrument.constant.Systematism;
import com.cludove.dirigible.instrument.expression.UniversalExpression;
import com.cludove.dirigible.instrument.protocol.body.Attachment;
import com.cludove.dirigible.module.DBScheduler;
import com.cludove.dirigible.bridge.Assembly;
import com.cludove.dirigible.bridge.Commissary;
import com.cludove.dirigible.bridge.Detecter;
import com.cludove.dirigible.bridge.Register;
import com.cludove.dirigible.bridge.Rudder;

public class Rudder {

    private Rudder() {
    }

    public static Rudder getInstance() {
        return new Rudder();
    }

    public void start(String requestBuddle, HttpServletRequest request, HttpServletResponse response, String language,
                      String encoding, Map<String, Object> parameters, Map<String, Object> variants, String[] codes,
                      Attachment bindingFile, List<Attachment> attachmentFiles, String context, String userAgent,
                      Map<String, Object> journal) throws Throwable {

        /**拼接请求地址*/
        String requestAddress = "RemoteAddr: " + request.getRemoteAddr() + ", RemoteHost: " + request.getRemoteHost()
                + ", RemotePort: " + request.getRemotePort() + ", RemoteUser: " + request.getRemoteUser();

        /**Parameter中获取请求路径*/
        String requestPath = (String) parameters.get(Logon.REQUEST_PATH);
        String[] useDomains = Parameter.getStringItem(Systematism.RESOURCE_NAME, Parameter.CONFIGURATION_PATH,
                Parameter.PARAMETER_CONFIGURATION, Systematism.USE_DOMAIN).split(Separator.ITEM);
        boolean isUseDomain = Boolean.FALSE.booleanValue();
        String useDomain = "http://www.w3.org/";
        /**检测是否http://www.w3.org/前缀开头*/
        if (useDomains != null && useDomains.length > 0)
            for (int i = 0; i < useDomains.length; i++) {
                useDomain = useDomains[i];
                isUseDomain = requestPath.startsWith(useDomain);
                if (isUseDomain)
                    break;
            }
        if (!isUseDomain) {
            response.sendRedirect(useDomain);
            return;
        }
        try {
            /**获取头部信息 都没有就获取IP地址*/
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getRemoteAddr();

            journal.put(Journal.REQUEST_IP, ip);

            journal.put(Journal.RECORD_CONTENT, journal.get(Journal.RECORD_CONTENT) + "<" + Direct.REQUEST
                    + " name='请求地址'><![CDATA[" + ip + "]]></" + Direct.REQUEST + ">");
            // 请求标记结束
            journal.put(Journal.RECORD_CONTENT, journal.get(Journal.RECORD_CONTENT) + "</" + Direct.REQUEST_CLUDOVE + ">");

            // 准备标记开始
            journal.put(Journal.RECORD_CONTENT, journal.get(Journal.RECORD_CONTENT) + "<" + Direct.PREPARATORY_CLUDOVE + ">");
            journal.put(Journal.RECORD_CONTENT, journal.get(Journal.RECORD_CONTENT) + "<" + Direct.PREPARATORY
                    + " name='运行准备'><![CDATA[" + requestAddress + " -> " + requestPath + "]]></" + Direct.PREPARATORY + ">");

            Map<String, Object> buddle = Detecter.getInstance().detectBuddle(requestBuddle, journal);

            if (requestBuddle == null)
                requestBuddle = String.valueOf(buddle.get(Resource.IDENTIFIER));

            journal.put(Resource.BUDDLE_ID, String.valueOf(buddle.get(Resource.IDENTIFIER)));
            journal.put(Journal.BUDDLE_NAME, String.valueOf(buddle.get(Resource.NAME)));

            Map<String, Object> style = Detecter.getInstance().detectStyle((String) parameters.get(Logon.STYLE_SETTING), journal);

            String sessionId = new String();
            if (parameters.get(Logon.SESSION_ID) == null
                    || !(String.valueOf(parameters.get(Logon.SESSION_ID)).trim().length() > 0)
                    || "NULL".equals(String.valueOf(parameters.get(Logon.SESSION_ID)).trim().toUpperCase()))
                sessionId = request.getSession().getId();
            else
                sessionId = String.valueOf(parameters.get(Logon.SESSION_ID)).trim();

            parameters.put(
                    Logon.LOCALHOST_ADDRESS,
                    "".equals(Parameter.getStringItem(Systematism.RESOURCE_NAME, Parameter.CONFIGURATION_PATH,
                            Parameter.PARAMETER_CONFIGURATION, Systematism.MANAGE_ADDRESS)) ? InetAddress.getLocalHost()
                            .getHostAddress() : Parameter.getStringItem(Systematism.RESOURCE_NAME, Parameter.CONFIGURATION_PATH,
                            Parameter.PARAMETER_CONFIGURATION, Systematism.MANAGE_ADDRESS));

            parameters.put(Logon.REMOTE_ADDR, ip);
            parameters.put(Logon.USER_AGENT, userAgent);
            parameters.put(Logon.SESSION_ID, sessionId);
            parameters.put(Logon.STYLE_SETTING, style.get(Resource.IDENTIFIER));
            if (!Catalog.LOGON_BUILD_PROCESS.equals(parameters.get(Direct.CATALOG)))
                parameters.put(
                        Logon.SIGNED,
                        String.valueOf(Register.getInstance().isLogon((String) request.getSession().getAttribute(Logon.ACCOUNT),
                                sessionId)));
            if (Boolean.parseBoolean(String.valueOf(parameters.get(Logon.SIGNED))))
                Register.getInstance().loadLogonInformation(request, parameters, sessionId);

            if (DispersedBasis.clusterIdentifier != null)
                parameters.put(Direct.CLUSTER_IDENTIFIER, DispersedBasis.clusterIdentifier);

            if (!Boolean.valueOf(String.valueOf(buddle.get(Buddle.IS_ACTIVE))).booleanValue())
                throw new BaseException(requestBuddle, "com.cludove.dirigible.bridge.Rudder.BuddleIsInactive");

            /* 当资源不为空时系统应响应欢迎界面，否则应根据首要选项选择正确的目录 */
            if (parameters.get(Direct.CATALOG) == null || !(String.valueOf(parameters.get(Direct.CATALOG)).trim().length() > 0))
                handleWelcome(requestBuddle, request, response, parameters, variants, language, encoding, sessionId);
            else
                handleAction(requestBuddle, request, response, parameters, variants, codes, bindingFile, attachmentFiles,
                        context, String.valueOf(parameters.get(Logon.STYLE_SETTING)), language, encoding,
                        Boolean.valueOf(String.valueOf(buddle.get(Resource.IDENTIFIER))).booleanValue(),
                        String.valueOf(buddle.get(Systematism.VALIDATOR)), userAgent, sessionId, journal,
                        Boolean.valueOf(String.valueOf(parameters.get(Direct.IS_PREVIEW))),
                        Boolean.valueOf(String.valueOf(parameters.get(Direct.IS_SINGLE))),
                        Boolean.valueOf(String.valueOf(parameters.get(Direct.IS_REFER))));
        } catch (InvocationTargetException ite) {
            // 获取目标异常
            Throwable t = ite.getTargetException();

            DailyLogger.logThrowable(t);

            DailyLogger.logThrowable(t, journal);

            if (response != null && response.getOutputStream() != null)
                DataBase.assembleResponseException(
                        request,
                        response,
                        parameters.get(Logon.STYLE_SETTING) != null ? String.valueOf(parameters.get(Logon.STYLE_SETTING))
                                : Parameter.getStringItem(Systematism.RESOURCE_NAME, Parameter.CONFIGURATION_PATH,
                                Parameter.PARAMETER_CONFIGURATION, Systematism.DEFAULT_STYLE),
                        language,
                        (t.getMessage() != null ? t.getMessage() : "")
                                + (language != null && BaseException.class.getName().equals(t.getClass().getName()) ? (Separator.ITEM
                                + Separator.BLANK + ErrorManager.getInstance().getDefinition(language,
                                ((BaseException) t).getAppendMessage()))
                                : ""), encoding != null ? encoding : IOBase.ENCODING, journal, requestBuddle);
        } catch (Exception e) {
            DailyLogger.logException(e);

            DailyLogger.logException(e, journal);

            if (response != null && response.getOutputStream() != null)
                DataBase.assembleResponseException(
                        request,
                        response,
                        parameters.get(Logon.STYLE_SETTING) != null ? String.valueOf(parameters.get(Logon.STYLE_SETTING))
                                : Parameter.getStringItem(Systematism.RESOURCE_NAME, Parameter.CONFIGURATION_PATH,
                                Parameter.PARAMETER_CONFIGURATION, Systematism.DEFAULT_STYLE),
                        language,
                        (e.getMessage() != null ? e.getMessage() : "")
                                + (language != null && BaseException.class.getName().equals(e.getClass().getName()) ? (Separator.ITEM
                                + Separator.BLANK + ErrorManager.getInstance().getDefinition(language,
                                ((BaseException) e).getAppendMessage()))
                                : ""), encoding != null ? encoding : IOBase.ENCODING, journal, requestBuddle);
        }
    }

    private void handleWelcome(String requestBuddle, HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> parameters, Map<String, Object> variants, String language, String encoding, String sessionId)
            throws Exception {

        String requestURI = request.getRequestURI();
        if (requestURI.lastIndexOf(Separator.HTTP_PATH) >= 0)
            requestURI = requestURI.substring(0, requestURI.lastIndexOf(Separator.HTTP_PATH));
        StringBuffer address = new StringBuffer(requestURI);

        address.append(Separator.HTTP_PATH);
        address.append(requestBuddle);

        address.append(Separator.HTTP_PATH);
        address.append(parameters.get(Logon.STYLE_SETTING));

        address.append(Separator.HTTP_PATH);
        address.append(language);

        response.sendRedirect(address.toString());
    }

    private void handleLogon(String requestBuddle, HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> parameters, Map<String, Object> variants, String styleId, String language, String encoding,
                             String sessionId, Map<String, Object> journal, boolean isPreview, boolean isSingle, boolean isRefer) throws Throwable {

        Map<String, Object> catalog = Detecter.getInstance().detectCatalog(requestBuddle, Catalog.LOGON_BUILD_PROCESS, journal);

        File style = DataBase.getStyle(language, requestBuddle, Catalog.LOGON_BUILD_PROCESS, styleId, false, false, false, false);

        boolean isKeepHeader;
        if (new StringBuffer(DataBase.IDENTITY).append(Separator.REFERENCE).append(Archives.XSL).toString()
                .equals(style.getName())) {
            response.setContentType(new StringBuffer("text/xml").append("; charset=").append(encoding).toString());
            isKeepHeader = Boolean.TRUE.booleanValue();
        } else {
            response.setContentType(new StringBuffer(String.valueOf(catalog.get(Resource.CONTENT_TYPE))).append("; charset=")
                    .append(encoding).toString());
            isKeepHeader = Boolean.FALSE.booleanValue();
        }

        StringBuffer contextBuffer = new StringBuffer();

        // contextBuffer.append(Assembly.assembleParameter(parameters));
        // contextBuffer.append(Assembly.assembleParameter(variants));
        contextBuffer.append(Assembly.assembleData(
                response,
                requestBuddle,
                Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT), sessionId,
                        Logon.ACCOUNT),
                Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT), sessionId,
                        Logon.SECRET),
                Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT), sessionId,
                        Logon.SECRECY), sessionId, parameters, variants, new String[] {}, null, null, new String(),
                (String) parameters.get(Logon.STYLE_SETTING), language, catalog, null, new HashMap<String, Object>(),
                Boolean.valueOf(String.valueOf(parameters.get(Direct.IS_PREVIEW)))));

        response.getOutputStream().write(
                DataBase.convert(
                        contextBuffer.toString(),
                        requestBuddle + "." + style.getName(),
                        DataBase.getStyleStream(
                                new StringBuffer(requestBuddle).append(Separator.REFERENCE).append(Catalog.LOGON_BUILD_PROCESS)
                                        .toString(), style), isKeepHeader, false, false, journal).getBytes());
    }

    private void handleAction(String requestBuddle, HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> parameters, Map<String, Object> variants, String[] codes, Attachment bindingFile,
                              List<Attachment> attachmentFiles, String context, String styleId, String language, String encoding,
                              boolean isPermitBuddle, String validator, String userAgent, String sessionId, Map<String, Object> journal,
                              boolean isPreview, boolean isSingle, boolean isRefer) throws Throwable {

        // 准备标记结束
        journal.put(Journal.RECORD_CONTENT, journal.get(Journal.RECORD_CONTENT) + "</" + Direct.PREPARATORY_CLUDOVE + ">");

        Map<String, Object> catalog = Detecter.getInstance().detectCatalog(requestBuddle,
                String.valueOf(parameters.get(Direct.CATALOG)), journal);

        boolean isNoLog = DataBase.isNoLog(catalog);

        journal.put(Resource.CATALOG_ID, String.valueOf(parameters.get(Direct.CATALOG)));
        journal.put(Journal.CATALOG_NAME, String.valueOf(parameters.get(Direct.CATALOG)));

        journal.put(Journal.ACCOUNT_NAME, String.valueOf(parameters.get(Logon.ACCOUNT)));

        /* 根据插件的isPermit配置项判断是否需要安全和保密验证 */
        /* 登录处理:0,不登录使用;1,登录使用;2,判断登录(重定向); */
        /* 如果没有登录则先进行登录操作 */
        if (Catalog.LOGON_BUILD_PROCESS.equals(parameters.get(Direct.CATALOG))
                || (isPermitBuddle && catalog != null && Boolean.parseBoolean(String.valueOf(catalog.get(DBScheduler.IS_PERMIT))) && !Boolean
                .parseBoolean(String.valueOf(parameters.get(Logon.SIGNED)))))
            if (Catalog.LOGON_BUILD_PROCESS.equals(parameters.get(Direct.CATALOG))) {

                Register.getInstance().logon(requestBuddle, request, response, parameters, validator);

                // 登陆/登出类型为0
                journal.put(Journal.JOURNAL_TYPE, "0");

                if (!isNoLog)
                    DailyLogger.logJournal(journal, Journal.OPERATE_RESULT_SUCCESS);

                handleLogon(requestBuddle, request, response, parameters, variants, styleId, language, encoding, sessionId,
                        journal, isPreview, isSingle, isRefer);
                return;
            } else {
                try {
                    Register.getInstance().logon(requestBuddle, request, response, parameters, validator);

                    DailyLogger.logJournal(journal, 0, Journal.OPERATE_RESULT_SUCCESS);
                } catch (Exception e) {
                    response.sendRedirect((String) parameters.get(Logon.REQUEST_PATH));

                    journal.put(Journal.JOURNAL_TYPE, "0");

                    if (!(parameters.get(Logon.ACCOUNT) == null || "".equals(parameters.get(Logon.ACCOUNT))))
                        DailyLogger.logException(e, journal);

                    return;
                }
            }

        if (Catalog.LOGOUT_REMOVE_PROCESS.equals(String.valueOf(catalog.get(Resource.IDENTIFIER)))) {
            parameters.put(Logon.SIGNED, Boolean.FALSE.toString());
            journal.put(Journal.JOURNAL_TYPE, "0");
        } else
            journal.put(Journal.JOURNAL_TYPE, "-1");

        StringBuffer contextBuffer = new StringBuffer();
        contextBuffer.append(Assembly.assembleParameter(parameters));
        contextBuffer.append(Assembly.assembleVariant(parameters, variants));
        if (parameters.get(Direct.CATALOG) != null
                && Commissary.getInstance().isRegiment(String.valueOf(catalog.get(Resource.IDENTIFIER)))) {
            assembleResponseData(
                    requestBuddle,
                    request,
                    response,
                    parameters,
                    variants,
                    styleId,
                    language,
                    encoding,
                    String.valueOf(catalog.get(Resource.BUDDLE_ID)),
                    String.valueOf(catalog.get(Resource.IDENTIFIER)),
                    "text/xml",
                    isNoLog,
                    contextBuffer.append(
                            Commissary.getInstance().regiment(requestBuddle, request, response, parameters, bindingFile,
                                    language, encoding, String.valueOf(catalog.get(Resource.IDENTIFIER)))).toString(), journal,
                    isPreview, isSingle, isRefer);
        } else if (parameters.get(Direct.CATALOG) != null
                && Catalog.RESOURCE_STRUCTURE_VIEW.equals(parameters.get(Direct.CATALOG)))

            assembleResponseData(requestBuddle, request, response, parameters, variants, styleId, language, encoding,
                    String.valueOf(catalog.get(Resource.BUDDLE_ID)), String.valueOf(catalog.get(Resource.IDENTIFIER)),
                    "text/xml", isNoLog,
                    contextBuffer.append(Assembly.checkResourceStructure(requestBuddle, parameters, styleId, language, journal))
                            .toString(), journal, isPreview, isSingle, isRefer);
        else if (parameters.get(Direct.CATALOG) != null && Catalog.OPERATOR_STRUCTURE_VIEW.equals(parameters.get(Direct.CATALOG)))
            assembleResponseData(requestBuddle, request, response, parameters, variants, styleId, language, encoding,
                    String.valueOf(catalog.get(Resource.BUDDLE_ID)), String.valueOf(catalog.get(Resource.IDENTIFIER)),
                    "text/xml", isNoLog, contextBuffer.append(checkOperatorStructure()).toString(), journal, isPreview, isSingle,
                    isRefer);
        else
            handleCatalogAction(requestBuddle, request, response, parameters, variants, codes, bindingFile, attachmentFiles,
                    context, styleId, language, encoding, validator, sessionId, journal, isPreview, isSingle, isRefer);
    }

    private void handleCatalogAction(String requestBuddle, HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> parameters, Map<String, Object> variants, String[] codes, Attachment bindingFile,
                                     List<Attachment> attachmentFiles, String context, String styleId, String language, String encoding, String validator,
                                     String sessionId, Map<String, Object> journal, boolean isPreview, boolean isSingle, boolean isRefer) throws Throwable {

        String requestCatalog = Commissary.getInstance().checkCatalogId(requestBuddle, parameters);

        Map<String, Object> catalog = Detecter.getInstance().detectCatalog(requestBuddle, requestCatalog, journal);

        if (catalog == null)
            throw new BaseException("功能目录：" + (String) parameters.get(Direct.CATALOG), "com.cludove.ResourceIsNotDefined");
        else {
            journal.put(Resource.CATALOG_ID, String.valueOf(catalog.get(Resource.IDENTIFIER)));
            journal.put(Journal.CATALOG_NAME, String.valueOf(catalog.get(Resource.NAME)));
            // 功能的类型为1
            journal.put(Journal.JOURNAL_TYPE, "1");

            boolean isNoLog = DataBase.isNoLog(catalog);

            if ((Boolean.valueOf(String.valueOf(catalog.get(DBScheduler.IS_PERMIT))).booleanValue() && !Boolean
                    .parseBoolean(String.valueOf(parameters.get(Logon.SIGNED))))) {
                Register.getInstance().logon(requestBuddle, request, response, parameters, validator);

                if (!isNoLog)
                    DailyLogger.logJournal(journal, 0, Journal.OPERATE_RESULT_SUCCESS);
            }

            assembleResponseData(requestBuddle, request, response, parameters, variants, styleId, language, encoding,
                    String.valueOf(catalog.get(Resource.BUDDLE_ID)), String.valueOf(catalog.get(Resource.IDENTIFIER)),
                    String.valueOf(catalog.get(Resource.CONTENT_TYPE)), isNoLog, Assembly.assembleData(
                            response,
                            requestBuddle,
                            Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT),
                                    sessionId, Logon.ACCOUNT),
                            Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT),
                                    sessionId, Logon.SECRET),
                            Register.getInstance().getInformation((String) request.getSession().getAttribute(Logon.ACCOUNT),
                                    sessionId, Logon.SECRECY), request.getSession().getId(), parameters, variants, codes,
                            bindingFile, attachmentFiles, context, styleId, language, catalog, null, journal, isPreview),
                    journal, isPreview, isSingle, isRefer);
        }
    }

    private String checkOperatorStructure() throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new UniversalExpression().getAlgorithmFile());
            String operatorStructure = new String(IOBase.read(fis));
            if (operatorStructure.indexOf("?>") > 0)
                operatorStructure = operatorStructure.substring(operatorStructure.indexOf("?>") + 2);

            return operatorStructure;
        } finally {
            if (fis != null)
                fis.close();
        }
    }

    private void assembleResponseData(String requestBuddle, HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> parameters, Map<String, Object> variants, String styleId, String language, String encoding,
                                      String buddleId, String catalogId, String contentType, boolean isNoLog, String context, Map<String, Object> journal,
                                      boolean isPreview, boolean isSingle, boolean isRefer) throws Exception {

        if (context == null)
            return;

        if (Boolean.valueOf(String.valueOf(parameters.get(Direct.IS_REDIRECT))) && "text/html".equals(contentType)) {
            response.sendRedirect(String.valueOf(parameters.get(Direct.REDIRECT_URL)));
            return;
        } else if ("text/none".equals(contentType))
            return;
        else
            ;

        File style = DataBase.getStyle(language, requestBuddle, catalogId, styleId, isSingle, isRefer, isPreview, false);

        if (new StringBuffer(DataBase.IDENTITY).append(Separator.REFERENCE).append(Archives.XSL).toString()
                .equals(style.getName())
                || new StringBuffer(DataBase.PREVIEW).append(Separator.REFERENCE).append(Archives.XSL).toString()
                .equals(style.getName()))

            response.setContentType(new StringBuffer("text/html").append("; charset=").append(encoding).toString());

        else
            response.setContentType(new StringBuffer(contentType).append("; charset=").append(encoding).toString());

        if (context.length() > 0)
            ;
        else {
            context += Assembly.assembleParameter(parameters);
            context += Assembly.assembleVariant(parameters, variants);
        }

        String content = Assembly.convertContext(requestBuddle, parameters, variants, styleId, language, buddleId, catalogId,
                context, journal, isPreview, isSingle, isRefer);

        if (!isNoLog)
            DailyLogger.logJournal(journal, Journal.OPERATE_RESULT_SUCCESS);

        response.getOutputStream().write(content.getBytes());
    }

}