<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <title><g:layoutTitle default="AMMRF | Technique Finder" /></title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
    	<meta name="keywords" content="AMMRF, Australian Microscopy & Microanalysis Research Facility, NCRIS, National Collaborative Research Infrastructure Strategy, state-of-the-art flagship platforms, microscopy, microanalysis, electron, x-ray diffraction, spectroscopy nanostructural characterisation, services, nanotechnology, biotechnology, research, access, instruments, pulsed-laser local electrode atom probe, high-throughput cryo-electron tomography, high-resolution SEM, high-precision ion microprobe, ultra-high resolution TEM, TAP, travel & access program, linked laboratories, linked centres, facilities, nodes, Specimen Preparation, Biological, Materials, Live Cell, Culturing, Thermomechanical Processing, Advanced Ion Platforms, Nanoscale Mass Spectroscopy, Atom Probe Tomography, Ion Milling and Machining, Ion Implantation, Light and Laser Optics, Fluorescence Imaging, Transmission Optical Microscopy, Metallurgical Microscopy, Live Cell Imaging, Scanned Probe Techniques, Vibrational Spectroscopy, Atomic Force Microscopy, Scanning Tunneling Microscopy, Raman Spectroscopy, Scanning Electron Microscopy, Analytical Spectroscopy, In-situ Imaging and Testing, Metrology, X-ray, Transmission Electron Microscopy, Analytical Spectroscopy, DiffractionPhase and Z-contrast Imaging, Cryo Techniques, Visualisation and Simulation, Computed Spectroscopy, Computed Diffraction, Image Analysis">
	
    	<meta name="description" content=""><br>
    	<meta name="language" content="english">
		<meta name="distribution" content="Global">
    	<meta name="robots" content="index,follow">
		<meta name="revisit-after" content="7 days">
		<meta name="email" content="">
		<meta name="publisher" content="">
		<meta name="copyright" content="Copyright 2010 AMMRF">
        
        <style type="text/css">
			@import url(${resource(dir:'css',file:'layout.css')});
			@import url(${resource(dir:'css',file:'styles.css')});
			@import url(${resource(dir:'css',file:'nav.css')});
			@import url(${resource(dir:'css',file:'technique_finder.css')});
		</style>
		
        <link rel="shortcut icon" href="${resource(dir:'images/ammrf',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="jquery"/>
        <g:layoutHead />
    </head>
    
    <body>
    	<g:set var="ammrfWebsiteURL" value="${grailsApplication.config.tf.ammrf.website.url}" />
		<!-- Page-Title for Robots -->
		<h1 id="top" class="hidden">AMMRF - Technique Finder</h1>
	
		
		<div id="logo"><a href="${ammrfWebsiteURL}index.php" title="" tabindex="0"><img src="${resource(dir:'images/ammrf',file:'logo.gif')}" border="0" alt="Logo: AMMRF"></a></div>
		<div id="head"><img src="${resource(dir:'images/ammrf',file:'txt_head.gif')}" alt="Australian Microscopy & Microanalysis Research Facility (AMMRF)"></div>
		<div id="nav">
			<g:staticContent code="tf.menu"/>
		</div>

		<div id="main">
		    <!-- IMAGES BAR -->
		    <div id="imageBar"><img class="bar" src="${resource(dir:'images/ammrf/micro',file:'nano-donuts.jpg')}"><img class="bar" src="${resource(dir:'images/ammrf/micro',file:'cluster.gif')}"><img class="bar" src="${resource(dir:'images/ammrf/micro',file:'kangaroosperm.jpg')}"><img src="${resource(dir:'images/ammrf/micro',file:'marsh1.jpg')}" width="96" height="70" class="bar"><img class="bar" src="${resource(dir:'images/ammrf/micro',file:'fib1.jpg')}" alt="Confocal Micrograph"></div>
		    
<!-- CONTENT -->
		    <g:pageProperty name="page.body"/>
<!-- END OF CONTENT -->
			
			<style type="text/css">
			<!--
			.style1 {font-size: 10px}
			-->
			</style>
			<div id="footer">
			    <p id="attribution_ammrf">&copy; 2007-2010 AMMRF | <a href="mailto:feedback@ammrf.org.au" class="style1 style1">Feedback</a> | <a id="footer" href="${ammrfWebsiteURL}disclaimer.php" title="disclaimer">Disclaimer</a></p>
			    <p id="attribution_intersect"> <a class="intersect_logo_link" href="http://www.intersect.org.au/" target="_blank"></a>Developed by <a href="http://www.intersect.org.au/" target="_blank">Intersect Australia</a></p>
			    <g:staticContent code="tf.tracking.ammrf"/>
			    <g:staticContent code="tf.tracking.intersect"/>
			</div>
		
		</div>

<div id="infobox">
	<g:pageProperty name="page.infobox"/>
</div>

	</body>
</html>
