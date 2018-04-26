 <%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <script src="js/jquery.cookie.js"></script> 
<sec:authorize access="hasRole('ROLE_USER')">
  <header class="main-header">
    <!-- Logo -->
    <a href="loginlist.jsp" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>D</b>G</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>DG Generator</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
       <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
            <li class="dropdown user user-menu dropdown-toggle" style="display: block;">
            <c:if test="${pageContext.request.userPrincipal.name != null}">
				Welcome  ${pageContext.request.userPrincipal.name} 
			</c:if>
          </li>
          <!-- Control Sidebar Toggle Button -->
        </ul>
        </div>
    </nav>
  </header>
  	<!-- For login user -->
		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="images/logo.png" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>DG GenraterSet APP</p>
        </div>
      </div>
      <ul class="sidebar-menu">
      <li><a href="#"><i class="fa fa-camera-retro"></i><span>Under Development</span></a></li>
            <li><a href="<c:url value="/logout" />"><i class="fa fa-sign-out"></i> <span>Logout</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
   <header class="main-header">
    <!-- Logo -->
    <a href="loginlist.jsp" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>D</b>G</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>DG Generator</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
       <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
            <li class="dropdown user user-menu dropdown-toggle" style="display: block;">
            <c:if test="${pageContext.request.userPrincipal.name != null}">
				Welcome  ${pageContext.request.userPrincipal.name} 
			</c:if>
          </li>
          <!-- Control Sidebar Toggle Button -->
        </ul>
        </div>
    </nav>
  </header>
  	<!-- For login user -->
		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="images/logo.png" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>DG GenraterSet APP</p>
        </div>
      </div>
      <ul class="sidebar-menu">
            <li><a href="#addimage"><i class="fa fa-picture-o"></i><span>Image Upload</span></a></li>
			<li><a href="#componentpage"><i class="fa fa-map-o"></i><span>Component Details</span></a></li>
           <!--  <li><a href="#devicesetup"><i class="fa fa-camera-retro"></i><span>Image Builder</span></a></li> -->
            <li><a href="#parameterlist"><i class="fa fa-pencil-square-o"></i><span>Parameter Details</span></a></li>
            <li><a href="#deviceprofilelist"><i class="fa fa-sliders"></i><span>Device Profile</span></a></li>
            <li><a href="#deviceinfo"><i class="fa fa-info-circle"></i><span>Device Info</span></a></li>
            <li><a href="#dashboardlist"><i class="fa fa-ioxhost" aria-hidden="true"></i><span>Dash Board Details</span></a></li>
          <!--   <li><a href="#devicemaster"><i class="fa fa-ioxhost" aria-hidden="true"></i><span>Device Master</span></a></li> -->
            <li><a href="#analogdevicesetting"><i class="fa fa-cog"></i><span>Analog Device Setting</span></a></li>
            <li><a href="<c:url value="/logout" />"><i class="fa fa-sign-out"></i> <span>Logout</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  	<!-- <script type="text/javascript">
		$.cookie("redirected",window.location.pathname);
		window.location ="login";
		</script> -->
  </sec:authorize> 
