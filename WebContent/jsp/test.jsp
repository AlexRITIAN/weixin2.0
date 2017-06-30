<%@page import="java.lang.management.ManagementFactory"
%><%@page import="java.lang.reflect.UndeclaredThrowableException"
%><%@page import="java.net.URLEncoder"
%><%@page import="java.text.DecimalFormat"
%><%@page import="java.text.DecimalFormatSymbols"
%><%@page import="java.text.NumberFormat"
%><%@page import="java.text.SimpleDateFormat"
%><%@page import="java.util.Date"
%><%@page import="java.util.HashMap"
%><%@page import="java.util.Locale"
%><%@page import="java.util.Map"
%><%@page import="java.util.Properties"
%><%@page import="java.util.ResourceBundle"
%><%@page import="java.util.TreeMap"
%><%@page import="javax.management.Attribute"
%><%@page import="javax.management.AttributeList"
%><%@page import="javax.management.InstanceNotFoundException"
%><%@page import="javax.management.MBeanServerConnection"
%><%@page import="javax.management.MBeanServerInvocationHandler"
%><%@page import="javax.management.ObjectName"
%><%@page import="javax.management.QueryExp"
%><%@page import="javax.management.openmbean.CompositeData"
%><%@page import="javax.management.openmbean.TabularData"
%><%@page import="javax.management.remote.JMXConnector"

%><%@page import="org.apache.log4j.Logger"

%><%@page import="wt.access.AccessControlException"
%><%@page import="wt.intersvrcom.SiteMonitorMBean"
%><%@page import="wt.jmx.core.MBeanRegistry"
%><%@page import="wt.jmx.core.MBeanUtilities"
%><%@page import="wt.jmx.core.SelfAwareMBean"
%><%@page import="wt.jmx.core.mbeans.Dumper"
%><%@page import="wt.log4j.LogR"
%><%@page import="wt.manager.RemoteServerManager"
%><%@page import="wt.manager.jmx.MethodServerMProxyMBean"
%><%@page import="wt.method.jmx.JmxConnectInfo"
%><%@page import="wt.method.jmx.MethodServer"
%><%@page import="wt.util.WTAppServerPropertyHelper"
%><%@page import="wt.util.WTContext"
%><%@page import="wt.util.WTProperties"
%><%@page import="wt.util.jmx.WDSJMXConnector"
%><%@page import="wt.util.jmx.AccessUtil"
%><%@page import="wt.util.jmx.JmxConnectUtil"
%><%@page import="wt.util.jmx.serverStatusResource"

%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
private static final String  windchillWebAppPath;
private static final String  whcWebAppPath;
private static final String  solrWebAppPath;
private static final String  helpUrl;
private static final ObjectName  dumperMBeanName = newObjectName( "com.ptc:wt.subsystem=Dumper" );
private static final ObjectName  runtimeMBeanName = newObjectName( ManagementFactory.RUNTIME_MXBEAN_NAME );
private static final ObjectName  gcMonitorMBeanName = newObjectName( "com.ptc:wt.subsystem=Monitors,wt.monitorType=GarbageCollection" );
private static final ObjectName  memMonitorMBeanName = newObjectName( "com.ptc:wt.subsystem=Monitors,wt.monitorType=Memory" );
private static final ObjectName  cpuMonitorMBeanName = newObjectName( "com.ptc:wt.subsystem=Monitors,wt.monitorType=ProcessCpuTime" );
private static final ObjectName  osMBeanName = newObjectName( ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME );
private static final ObjectName  methodServerMProxyMBeanName = newObjectName( "com.ptc:wt.processGroup=MethodServers" );
private static final ObjectName  methodServerMBeanName = newObjectName( "com.ptc:wt.type=MethodServer" );
private static final ObjectName  methodContextsMBeanName = newObjectName( "com.ptc:wt.subsystem=Monitors,wt.monitorType=MethodContexts" );
private static final ObjectName  seRequestMonitorMBeanName = newObjectName( "com.ptc:wt.servlet.system=WebAppContexts,wt.webAppContext=" + windchillWebAppPath +
                                                                            ",wt.subsystem=Monitors,wt.servlet.subsystem=ServletRequests" );
private static final ObjectName  sessionMonitorMBeanName = newObjectName( "com.ptc:wt.servlet.system=WebAppContexts,wt.webAppContext=" + windchillWebAppPath +
                                                                          ",wt.subsystem=Monitors,wt.servlet.subsystem=ServletSessions" );
private static final ObjectName  whcRequestMonitorMBeanName = newObjectName( "com.ptc:wt.servlet.system=WebAppContexts,wt.webAppContext=" + whcWebAppPath +
                                                                             ",wt.subsystem=Monitors,wt.servlet.subsystem=ServletRequests" );
private static final ObjectName  solrRequestMonitorMBeanName = newObjectName( "com.ptc:wt.servlet.system=WebAppContexts,wt.webAppContext=" + solrWebAppPath +
																				",wt.subsystem=Monitors,wt.servlet.subsystem=ServletRequests" );

private static final String  dumperMBeanAttrs[] = new String[] { "DeadlockedThreadIds" };
private static final String  runtimeMBeanAttrs[] = new String[] { "StartTime", "Uptime" };
private static final String  msRuntimeMBeanAttrs[] = new String[] { "StartTime", "Name", "Uptime" };
private static final String  dsRuntimeMBeanAttrs[] = new String[] { "Name", "Uptime" };
private static final String  gcMonitorMBeanAttrs[] = new String[] { "PercentTimeSpentInGCThreshold", "RecentPercentTimeSpentInGC", "OverallPercentTimeSpentInGC" };
private static final String  memMonitorMBeanAttrs[] = new String[] { "HeapPercentUsageThreshold", "HeapPercentUsage", "PermGenPercentUsageThreshold", "PermGenPercentUsage" };
private static final String  cpuMonitorMBeanAttrs[] = new String[] { "ProcessPercentCpuThreshold", "RecentCpuData", "AverageProcessPercentCpu" };
private static final String  osMBeanAttrs[] = new String[] { "FreePhysicalMemorySize", "TotalPhysicalMemorySize", "FreeSwapSpaceSize", "TotalSwapSpaceSize", "SystemLoadAverage" };
private static final String  methodServerMBeanAttrs[] = new String[] { "JmxServiceURL" };
private static final String  methodContextsMBeanAttrs[] = new String[] { "MaxAverageActiveContextsThreshold", "RecentStatistics", "BaselineStatistics" };
private static final String  seRequestMonitorMBeanAttrs[] = new String[] { "MaxAverageActiveRequestsThreshold", "RequestTimeWarnThreshold", "RecentStatistics", "BaselineStatistics" };
private static final String  sessionMonitorMBeanAttrs[] = new String[] { "MaxAverageActiveSessionsThreshold", "ActiveSessions", "BaselineStatistics" };
private static final String  serverManagerMBeanAttrs[] = new String[] { "CacheMaster", "JmxServiceURL" };
private static final String  getAttributesSignature[] = { ObjectName[].class.getName(), QueryExp[].class.getName(), String[][].class.getName() };
private static final Object  methodServersGetAttrsArgs[] =
{
  new ObjectName[]
  {
    runtimeMBeanName,
    gcMonitorMBeanName,
    memMonitorMBeanName,
    cpuMonitorMBeanName,
    dumperMBeanName,
    methodServerMBeanName,
    methodContextsMBeanName,
    sessionMonitorMBeanName,
    seRequestMonitorMBeanName,
    whcRequestMonitorMBeanName,
    solrRequestMonitorMBeanName
  },
  null,
  new String[][]
  {
    msRuntimeMBeanAttrs,
    gcMonitorMBeanAttrs,
    memMonitorMBeanAttrs,
    cpuMonitorMBeanAttrs,
    dumperMBeanAttrs,
    methodServerMBeanAttrs,
    methodContextsMBeanAttrs,
    sessionMonitorMBeanAttrs,
    seRequestMonitorMBeanAttrs,
    seRequestMonitorMBeanAttrs,
    seRequestMonitorMBeanAttrs
  }
};
// collect data on method servers and server managers
%><% 
Integer  activeUsers = null;
Map<String,Object>  methodServerResults = null;
Map<String,Object>  serverManagerResults = null;
Throwable  resultRetrievalException = null;

  // establish JMX connection to default server manager to collect JMX data from server managers and method servers
  try ( JMXConnector smConnection = JmxConnectUtil.getDefaultServerManagerLocalConnector() )
  {
    final MBeanServerConnection  mbeanServer = smConnection.getMBeanServerConnection();

    // produce dynamic proxy for the "MethodServers" MBean in default server manager
    final MethodServerMProxyMBean  methodServersMBeanProxy =
            MBeanServerInvocationHandler.newProxyInstance( mbeanServer, methodServerMProxyMBeanName,
                                                           MethodServerMProxyMBean.class, false );

    // grab data from all method servers (yes, this is a bit ugly, but it is all done in one request -- no chatter)
    methodServerResults = methodServersMBeanProxy.invokeInfoOpInAllClusterMethodServers(
                            dumperMBeanName, "getAttributes", methodServersGetAttrsArgs, getAttributesSignature );
		System.out.print("=================>" + methodServerResults + "<================");
  }
	catch ( VirtualMachineError e )
	{
	  throw e;
	}
	catch ( Throwable t )
	{
	  resultRetrievalException = t;  // remember but otherwise eat exception
	}
 
  
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>test</h1>
	<% %>
	<%=methodServerResults %>
</body>
</html>
 <%!
  private static ObjectName  newObjectName( final String objectNameString )
  {
    try
    {
      return ( new ObjectName( objectNameString ) );
    }
    catch ( Exception e )
    {
      if ( e instanceof RuntimeException )
        throw (RuntimeException) e;
      throw new RuntimeException( e );
    }
  }
%>