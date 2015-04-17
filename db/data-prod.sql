-- data population script, use with --default-character-set=utf8 to pass it thru
use technique_finder;
-- initial admin accounts
insert into role (id, version, authority, description) values (1, 0, 'ROLE_SUPER', 'Super administrator');
insert into role (id, version, authority, description) values (2, 0, 'ROLE_ADMIN', 'Administrator');
insert into user (id, version, passwd, enabled, username, full_name, second_email) values (1, 0,'d033e22ae348aeb5660fc2140aec35850c4da997',1,'admin@ammrf.org.au', 'Primary super admin account', '');
insert into user (id, version, passwd, enabled, username, full_name, second_email) values (2, 0,'d033e22ae348aeb5660fc2140aec35850c4da997',1,'jenny.whiting@ammrf.org.au', 'Jenny Whiting', '');
insert into user (id, version, passwd, enabled, username, full_name, second_email) values (3, 0,'d033e22ae348aeb5660fc2140aec35850c4da997',1,'uli.eichhorn@ammrf.org.au', 'Uli Eichhorn', '');
insert into role_people (user_id, role_id) values (1, 1);
insert into role_people (user_id, role_id) values (1, 2);
insert into role_people (user_id, role_id) values (2, 1);
insert into role_people (user_id, role_id) values (2, 2);
insert into role_people (user_id, role_id) values (3, 1);
insert into role_people (user_id, role_id) values (3, 2);
-- static content
insert into static_content (id, version, name, text) values (1, 0, 'tf.home.quickGuide', '<h1>Technique Finder</h1>
<p>This tool is designed to help you identify and understand the diversity of microscopy and microanalysis techniques available to researchers through the AMMRF, without expecting any prior knowledge of the instruments.</p>
<p>You will find the contact details of our expert staff for each technique. They can provide you with all the information you need and guide you through the planning, training, data collection and interpretation stages of your experiment. Their expertise is extensive and they can help enourmously with getting great results.</p>');
insert into static_content (id, version, name, text) values (2, 0, 'tf.home.optionsExplanation', 'The application takes you through a series of choices to identify your particular type of experimental question. It will present you with a list of techniques that could be applied to address that question. Please click a button to get started.');
insert into static_content (id, version, name, text) values (3, 0, 'tf.biologyChoices.quickGuide', '<h1>Choices for biological sciences</h1>
<p>This selection process is based on the fact that many experiments in the biological sciences involve the interaction of two things. For instance, you might want to look at the interaction of one protein with another protein, a cell with the extracellular matrix, or possibly one thing within another such as metal ions in the hard tissue of insect teeth.</p>');
insert into static_content (id, version, name, text) values (4, 0, 'tf.biologyChoices.comparison.title', 'and their');
insert into static_content (id, version, name, text) values (5, 0, 'tf.biologyChoices.left.title', 'Step 1: Choose a leftOption');
insert into static_content (id, version, name, text) values (6, 0, 'tf.biologyChoices.right.title', 'Step 2: Choose your rightOption interest');
insert into static_content (id, version, name, text) values (7, 0, 'tf.physicsChoices.quickGuide', '<h1>Choices for physical sciences</h1>');
insert into static_content (id, version, name, text) values (8, 0, 'tf.physicsChoices.comparison.title', 'at the scale of');
insert into static_content (id, version, name, text) values (9, 0, 'tf.physicsChoices.left.title', 'Step 1: Choose a leftOption');
insert into static_content (id, version, name, text) values (10, 0, 'tf.physicsChoices.right.title', 'Step 2: Choose your rightOption interest');
insert into static_content (id, version, name, text) values (11, 0, 'tf.home.searchExplanation', 'If you know what you want to explore, type it into the search box and click \'go\'.');
insert into static_content (id, version, name, text) values (12, 0, 'tf.menu', '<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/index.php" class="navLevel1Active">Home</a></div>
<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/about.php" class="navLevel1">About Us</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/nodes.php" class="navLevel2">AMMRF Nodes</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/linkedlabs.php" class="navLevel2">Linked Laboratories</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/linkedcentres.php" class="navLevel2">Linked Centres</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/structure.php" class="navLevel2">Organisational Structure</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/ituag.php" class="navLevel2">International Advisory Group</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/careers.php" class="navLevel2">Careers</a></div>
<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/access.php" class="navLevel1">Access the AMMRF</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/tap.php" class="navLevel2">Travel and Access Program</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/teaching.php" class="navLevel2">Courses & Teaching</a></div>
<div style="margin-top: 8px; height: 12px;"><a class="navLevel1" href="techniquefinder.php">Technique Finder</a></div>
<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/facilities.php" class="navLevel1">Facilities</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/flagships.php" class="navLevel2">Flagship Instruments</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/instruments.php" class="navLevel2">All Instruments</a></div>
<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/info_centre.php" class="navLevel1">Info Centre</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/news.php" class="navLevel2">News</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/media_releases.php" class="navLevel2">Media Releases</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/events.php" class="navLevel2">Events</a></div>
<div style="margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/contacts.php" class="navLevel1">Contact</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/staff.php" class="navLevel2">Staff Directory</a></div>
<div style="margin-left:18px; margin-top:8px; height:12px;"><a href="http://www.ammrf.org.au/oholders.php" class="navLevel2">Office Holders</a></div>');
insert into static_content (id, version, name, text) values (13, 0, 'tf.home.allTechniquesExplanation', 'This list shows the techniques currently available at the AMMRF');
insert into static_content (id, version, name, text) values (14, 0, 'tf.home.infoboxContent', '<div style="border:1px solid #db66617; padding:10px;">
    <h2>For information please contact:</h2>
    <p><img src="/images/whiting.jpg" width="90" height="105" alt="Dr Jenny Whiting"></p>
    <p><a href="mailto:jenny.whiting@ammrf.org.au">Dr Jenny Whiting<br>
        </a>AMMRF Marketing &amp; Business Development Manager<br>
        <span class="content">Tel: +61 </span>2 9114 0566<br>
        Fax: +61 2 2 9351 7682<span class="content"><br>
            Email: <a href="mailto:jenny.whiting@ammrf.org.au">jenny.whiting@ammrf.org.au<br>
    </a></span><span class="content"><br>
        AMMRF Headquarters<br>
        Australian Centre for Microscopy &amp; Microanalysis<br>
        Madsen Building, F09<br>
        The University of Sydney </span><span class="content">NSW 2006</span></p>
  </div>
  <table width="100%"  border="0" cellpadding="0">
  </table>
  <p>&nbsp;</p>');
-- techniques
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (2, 0, 'Atomic Force Microscopy (AFM)', 'Atomic Force Microscopy (AFM) is able to reveal the topography, adhesive and mechanical properties of the sample surface down to a lateral resolution of 5-10 nm and vertical resolution of 0.1 nm, giving an image and/or spectra. ', 'Atomic Force Microscopy (AFM) is able to reveal the topography, adhesive and mechanical properties of the sample surface down to a lateral resolution of 5-10 nm and vertical resolution of 0.1 nm, giving an image and/or spectra. ', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (3, 0, 'Atom probe tomography', 'Atom probe tomography identifies the position and elemental nature of atoms in a sample down to a resolution of 0.04 nanometres. It generates data that can be reconstructed to give a 3-D representation of the samples\' atomic structure.', 'Atom probe tomography identifies the position and elemental nature of atoms in a sample down to a resolution of 0.04 nanometres. It generates data that can be reconstructed to give a 3-D representation of the samples\' atomic structure.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (17, 0, 'FACS ', 'Fluorescence Activated Cell Sorting (FACS) allows the simultaneous separation of up to four pure populations from a heterogenous suspension sample, based on the detection of fluorescent markers on cells, nuclei, microorganisms or beads from 200nm – 60um in size, at a speed of up to 90000 cells per second. The collected cells or particles may then be used for subsequent analysis, tissue culture or injection into experimental animal models. It also generates bivariate dot-plots and histograms which can be used to calculate population statistics and changes in mean fluorescence intensity within the sample.', 'Fluorescence Activated Cell Sorting (FACS) allows the simultaneous separation of up to four pure populations from a heterogenous suspension sample, based on the detection of fluorescent markers on cells, nuclei, microorganisms or beads from 200nm – 60um in size, at a speed of up to 90000 cells per second. The collected cells or particles may then be used for subsequent analysis, tissue culture or injection into experimental animal models. It also generates bivariate dot-plots and histograms which can be used to calculate population statistics and changes in mean fluorescence intensity within the sample.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (18, 0, 'Focused ion beam (FIB)', 'FIB/SEM allows a hard sample to be sectioned or shaped with an ion beam while being monitored by SEM. It can cut 10nm thick sections from very hard materials and shape needles for analysis by other techniques such as TEM and APT. It can also mediate chemical vapour deposition with a diameter of approximately 100nm The FIB can  cut sequential sections off a sample allowing a dataset of adjacent surface SEM images to be created and reconstructed into a 3-D image of the sample.', 'FIB/SEM allows a hard sample to be sectioned or shaped with an ion beam while being monitored by SEM. It can cut 10nm thick sections from very hard materials and shape needles for analysis by other techniques such as TEM and APT. It can also mediate chemical vapour deposition with a diameter of approximately 100nm The FIB can  cut sequential sections off a sample allowing a dataset of adjacent surface SEM images to be created and reconstructed into a 3-D image of the sample.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (19, 0, 'Field ion microscopy (FIM)', 'Field ion microscopy (FIM) identifies the positions, and to a limited degree, element identity of atoms on the surface of a sample down to a resolution of 0.04 nanometres.  It creates 2-D images of surface structures.', 'Field ion microscopy (FIM) identifies the positions, and to a limited degree, element identity of atoms on the surface of a sample down to a resolution of 0.04 nanometres.  It creates 2-D images of surface structures.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (22, 0, 'Flow cytometry ', 'Flow Cytometry allows the detection of extracellular or intracellular fluorescent markers on cells, nuclei, microorganisms or beads in a suspension, at a rate of 2000 – 10000 cells per second. It generates bivariate dot-plots and histograms which can be used to calculate population statistics and changes in mean fluorescence intensity within the sample.', 'Flow Cytometry allows the detection of extracellular or intracellular fluorescent markers on cells, nuclei, microorganisms or beads in a suspension, at a rate of 2000 – 10000 cells per second. It generates bivariate dot-plots and histograms which can be used to calculate population statistics and changes in mean fluorescence intensity within the sample.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (30, 0, 'laser microdissection', 'Laser microdissection allows the separation of cell clusters, single cells and subcellular components from tissue sections based on brightfield morphology or fluorescent markers at 10x, 20x, 40x, 63x or 100x magnification. The collected cells or subcellular components may then be used for subsequent DNA, RNA or protein analysis. It also generates images of the tissue section before and after laser microdissection, as well as of the collected cells.', 'Laser microdissection allows the separation of cell clusters, single cells and subcellular components from tissue sections based on brightfield morphology or fluorescent markers at 10x, 20x, 40x, 63x or 100x magnification. The collected cells or subcellular components may then be used for subsequent DNA, RNA or protein analysis. It also generates images of the tissue section before and after laser microdissection, as well as of the collected cells.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (38, 0, 'micro-CT', 'X-ray microtomography (MicroXCT) allows internal structural details to be revealed in a broad range of materials but is particularly well suited to porous specimens or materials where there is a large density difference between adjacent structural components. The available resolution is a function of sample size and density but in general ranges from about 15µm down to 1.5µm. A series of projection images are generated and can be processed into an interactive 3D model.', 'X-ray microtomography (MicroXCT) allows internal structural details to be revealed in a broad range of materials but is particularly well suited to porous specimens or materials where there is a large density difference between adjacent structural components. The available resolution is a function of sample size and density but in general ranges from about 15µm down to 1.5µm. A series of projection images are generated and can be processed into an interactive 3D model.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (40, 0, 'nano-CT', 'X-ray Nanotomography (Nano-XCT) allows internal structural details to be revealed in a broad range of materials but is particularly well suited to porous specimens or materials where there is a large density difference between adjacent structural components. Results may be viewed either as 2D slices or as 3D models. With specimen thickness in the order 200µm, 3D sub-volumes of 64µm and 16µm can be imaged at resolutions of 150nm and 50nm respectively.
', 'X-ray Nanotomography (Nano-XCT) allows internal structural details to be revealed in a broad range of materials but is particularly well suited to porous specimens or materials where there is a large density difference between adjacent structural components. Results may be viewed either as 2D slices or as 3D models. With specimen thickness in the order 200µm, 3D sub-volumes of 64µm and 16µm can be imaged at resolutions of 150nm and 50nm respectively.
', '', '');
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (41, 0, 'nanoSIMS', 'NanoSIMS allows elemental or isotopic mapping of a sample to a resolution of 50 - 200 nm, giving images that reveal the chemical composition of the surface. ', 'NanoSIMS allows elemental or isotopic mapping of a sample to a resolution of 50 - 200 nm, giving images that reveal the chemical composition of the surface. ', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (43, 0, 'NSOM ', 'Near-field Scanning microscopy (NSOM) reveals  the  topography and chemical composition of the sample surface down to a lateral resolution of 20 nm and vertical resolution of 2-5 nm, giving an image and/or spectra.', 'Near-field Scanning microscopy (NSOM) reveals  the  topography and chemical composition of the sample surface down to a lateral resolution of 20 nm and vertical resolution of 2-5 nm, giving an image and/or spectra.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (45, 0, 'OWLS', 'OWLS measures the change in refractive index of a sensor surface as molecules in solution, such as proteins, bind to it in real time, allowing the kinetics of binding to be determined. It can detect binding down to concentrations of ng/cm2 giving a plot of changes in refractive index.', 'OWLS measures the change in refractive index of a sensor surface as molecules in solution, such as proteins, bind to it in real time, allowing the kinetics of binding to be determined. It can detect binding down to concentrations of ng/cm2 giving a plot of changes in refractive index.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (49, 0, 'Raster Image Correlation Spectroscpy (RICS)', 'RICS measures the rate of movement of fluorescently labelled molecules in living cells in the microsecond range with single molecule sensitivity. It generates images with a colour scale indicating the rate of movement.', 'RICS measures the rate of movement of fluorescently labelled molecules in living cells in the microsecond range with single molecule sensitivity. It generates images with a colour scale indicating the rate of movement.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (51, 0, 'Scanning electron microscopy (SEM) - imaging', 'SEM - imaging reveals the surface structure of the sample down to a resolution of approximately 1nm giving an image of the surface.
• Secondary electron SEM shows the surface topography
• Backscattered SEM shows the surface with respect to the molecular mass of its constituents.', 'SEM - imaging reveals the surface structure of the sample down to a resolution of approximately 1nm giving an image of the surface.
• Secondary electron SEM shows the surface topography
• Backscattered SEM shows the surface with respect to the molecular mass of its constituents.', '', '');
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (52, 0, 'Scanning electron microscopy (SEM) - spectroscopy', 'SEM - spectroscopy reveals the elemental composition on the surface of a sample down to a depth of between 100nm and 5μm and with a lateral resolution down to about 50μm. Results can be displayed either as a graph or colour-coded image. ', 'SEM - spectroscopy reveals the elemental composition on the surface of a sample down to a depth of between 100nm and 5μm and with a lateral resolution down to about 50μm. Results can be displayed either as a graph or colour-coded image. ', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (61, 0, 'SIMS', 'SIMS measures the spatial elemental and isotopic composition of a sample at a resolution of 10 - 30 µm giving precise isotope ratio data in-situ.', 'SIMS measures the spatial elemental and isotopic composition of a sample at a resolution of 10 - 30 µm giving precise isotope ratio data in-situ.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (66, 0, 'Scanning tunelling microscopy (STM)', 'Scanning Tunnelling Microscopy (STM) reveals the local density of states (LDOS) of semi-conducting and conducting surfaces down to a lateral resolution of 0.1 nm and vertical resolution of 0.01 nm, giving an image and/or spectrum. ', 'Scanning Tunnelling Microscopy (STM) reveals the local density of states (LDOS) of semi-conducting and conducting surfaces down to a lateral resolution of 0.1 nm and vertical resolution of 0.01 nm, giving an image and/or spectrum. ', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (69, 0, 'Transmission electron microscopy - imaging', 'TEM - imaging reveals the fine structure of a sample in an ultrathin section or thin film of a sample down to a resolution of approximately 2.5 nm, or even to the atomic level for crystalline materials in high resolution TEM, generating images based on the electron density of regions within the sample.', 'TEM - imaging reveals the fine structure of a sample in an ultrathin section or thin film of a sample down to a resolution of approximately 2.5 nm, or even to the atomic level for crystalline materials in high resolution TEM, generating images based on the electron density of regions within the sample.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (70, 0, 'Transmission electron microscopy - spectroscopy', 'TEM - spectroscopy reveals the elemental composition in an ultrathin section or thin film of a sample down to a resolution of approximately 2.5 nm, or even to the atomic level for crystalline materials in high resolution TEM. Spectra are generated based on the energy or wavelength dispersion of the elements within the sample.', 'TEM - spectroscopy reveals the elemental composition in an ultrathin section or thin film of a sample down to a resolution of approximately 2.5 nm, or even to the atomic level for crystalline materials in high resolution TEM. Spectra are generated based on the energy or wavelength dispersion of the elements within the sample.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (76, 0, 'Thermal analysis', 'Thermal Analysis measures dimensional or phase changes as a function of time or temperature under a controlled atmosphere and static or dynamic conditions.', 'Thermal Analysis measures dimensional or phase changes as a function of time or temperature under a controlled atmosphere and static or dynamic conditions.', '', ''); 
insert into technique (id, version, name, summary, description, alternative_names, keywords) values (79, 0, 'ToFSIMS', 'ToF-SIMS reveals the elemental, isotopic and molecular structure of the top 1-2 nm of a solid sample down to sub-micronlateral resolution, giving mass spectral data as well as images for reconstruction.', 'ToF-SIMS reveals the elemental, isotopic and molecular structure of the top 1-2 nm of a solid sample down to sub-micronlateral resolution, giving mass spectral data as well as images for reconstruction.', '', ''); 
-- locations
insert into location (id, version, institution, priority, status, address, center_name, state) values (1, 0, 'The University of Sydney', 1,'ND','','','NSW'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (2, 0, 'The University of Queensland', 2,'ND','','','QLD'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (3, 0, 'The University of Western Australia', 3,'ND','','','WA'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (4, 0, 'The University of New South Wales', 4,'ND','','','NSW'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (5, 0, 'The Australian National University', 5,'ND','','','ACT'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (6, 0, 'SARF - the University of Adelaide', 6,'ND','','','SA'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (7, 0, 'SARF - University of South Australia', 7,'ND','','','SA'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (8, 0, 'SARF - Flinders University', 8,'ND','','','SA'); 
insert into location (id, version, institution, priority, status, address, center_name, state) values (9, 0, 'SARF - Ian Wark Research Institute', 9,'LL','','','SA'); 
-- options (biology)
insert into option_choice (id,version,name,science,type,priority) values (1,0,'Metabolites','BIOLOGY','LEFT',1);
insert into option_choice (id,version,name,science,type,priority) values (2,0,'Lipids','BIOLOGY','LEFT',2);
insert into option_choice (id,version,name,science,type,priority) values (3,0,'Drugs','BIOLOGY','LEFT',3);
insert into option_choice (id,version,name,science,type,priority) values (4,0,'Ions/metals/isotopes','BIOLOGY','LEFT',4);
insert into option_choice (id,version,name,science,type,priority) values (5,0,'DNA/RNA','BIOLOGY','LEFT',5);
insert into option_choice (id,version,name,science,type,priority) values (6,0,'Complex carbohydrates','BIOLOGY','LEFT',6);
insert into option_choice (id,version,name,science,type,priority) values (7,0,'Extracellular matrix','BIOLOGY','LEFT',7);
insert into option_choice (id,version,name,science,type,priority) values (8,0,'Functional proteins','BIOLOGY','LEFT',8);
insert into option_choice (id,version,name,science,type,priority) values (9,0,'Cytoskeleton','BIOLOGY','LEFT',9);
insert into option_choice (id,version,name,science,type,priority) values (10,0,'Cell surface ','BIOLOGY','LEFT',10);
insert into option_choice (id,version,name,science,type,priority) values (11,0,'Intracellular structures','BIOLOGY','LEFT',11);
insert into option_choice (id,version,name,science,type,priority) values (12,0,'Live cells','BIOLOGY','LEFT',12);
insert into option_choice (id,version,name,science,type,priority) values (13,0,'Fixed cells','BIOLOGY','LEFT',13);
insert into option_choice (id,version,name,science,type,priority) values (14,0,'Hard tissue','BIOLOGY','LEFT',14);
insert into option_choice (id,version,name,science,type,priority) values (15,0,'Soft tissue','BIOLOGY','LEFT',15);
insert into option_choice (id,version,name,science,type,priority) values (16,0,'Organs','BIOLOGY','LEFT',16);
insert into option_choice (id,version,name,science,type,priority) values (17,0,'Whole organism','BIOLOGY','LEFT',17);
insert into option_choice (id,version,name,science,type,priority) values (18,0,'interaction with metabolites','BIOLOGY','RIGHT',1);
insert into option_choice (id,version,name,science,type,priority) values (19,0,'interaction with lipids','BIOLOGY','RIGHT',2);
insert into option_choice (id,version,name,science,type,priority) values (20,0,'interaction with drugs','BIOLOGY','RIGHT',3);
insert into option_choice (id,version,name,science,type,priority) values (21,0,'interaction with ions/metals/isotopes','BIOLOGY','RIGHT',4);
insert into option_choice (id,version,name,science,type,priority) values (22,0,'interaction with DNA/RNA ','BIOLOGY','RIGHT',5);
insert into option_choice (id,version,name,science,type,priority) values (23,0,'interaction with complex carbohydrates','BIOLOGY','RIGHT',6);
insert into option_choice (id,version,name,science,type,priority) values (24,0,'interaction with extracellular matrix','BIOLOGY','RIGHT',7);
insert into option_choice (id,version,name,science,type,priority) values (25,0,'interaction with functional proteins','BIOLOGY','RIGHT',8);
insert into option_choice (id,version,name,science,type,priority) values (26,0,'interaction with cytoskeleton','BIOLOGY','RIGHT',9);
insert into option_choice (id,version,name,science,type,priority) values (27,0,'interaction with cell surface ','BIOLOGY','RIGHT',10);
insert into option_choice (id,version,name,science,type,priority) values (28,0,'interaction with intracellular structures','BIOLOGY','RIGHT',11);
insert into option_choice (id,version,name,science,type,priority) values (29,0,'interaction with live cells','BIOLOGY','RIGHT',12);
insert into option_choice (id,version,name,science,type,priority) values (30,0,'interaction with fixed cells','BIOLOGY','RIGHT',13);
insert into option_choice (id,version,name,science,type,priority) values (31,0,'interaction with hard tissue','BIOLOGY','RIGHT',14);
insert into option_choice (id,version,name,science,type,priority) values (32,0,'interaction with soft tissue','BIOLOGY','RIGHT',15);
insert into option_choice (id,version,name,science,type,priority) values (33,0,'interaction with organs','BIOLOGY','RIGHT',16);
insert into option_choice (id,version,name,science,type,priority) values (34,0,'interaction with whole organism','BIOLOGY','RIGHT',17);
insert into option_choice (id,version,name,science,type,priority) values (35,0,'Structure','BIOLOGY','RIGHT',18);
insert into option_choice (id,version,name,science,type,priority) values (36,0,'Migration','BIOLOGY','RIGHT',19);
insert into option_choice (id,version,name,science,type,priority) values (37,0,'10 mm','PHYSICS','RIGHT',1);
insert into option_choice (id,version,name,science,type,priority) values (38,0,'1 mm','PHYSICS','RIGHT',2);
insert into option_choice (id,version,name,science,type,priority) values (39,0,'10 μm','PHYSICS','RIGHT',3);
insert into option_choice (id,version,name,science,type,priority) values (40,0,'1 μm','PHYSICS','RIGHT',4);
insert into option_choice (id,version,name,science,type,priority) values (41,0,'10 nm','PHYSICS','RIGHT',5);
insert into option_choice (id,version,name,science,type,priority) values (42,0,'1 nm','PHYSICS','RIGHT',6);
insert into option_choice (id,version,name,science,type,priority) values (43,0,'2D Morphology','PHYSICS','LEFT',1);
insert into option_choice (id,version,name,science,type,priority) values (44,0,'3D Morphology','PHYSICS','LEFT',2);
insert into option_choice (id,version,name,science,type,priority) values (45,0,'1D Composition','PHYSICS','LEFT',3);
insert into option_choice (id,version,name,science,type,priority) values (46,0,'2D Composition','PHYSICS','LEFT',4);
insert into option_choice (id,version,name,science,type,priority) values (47,0,'3D Composition','PHYSICS','LEFT',5);
insert into option_choice (id,version,name,science,type,priority) values (48,0,'Isotopes','PHYSICS','LEFT',6);
insert into option_choice (id,version,name,science,type,priority) values (49,0,'Crystallographic Structure','PHYSICS','LEFT',7);
insert into option_choice (id,version,name,science,type,priority) values (50,0,'Bonding Structure','PHYSICS','LEFT',8);
insert into option_choice (id,version,name,science,type,priority) values (51,0,'Stresses and strains','PHYSICS','LEFT',9);
insert into option_choice (id,version,name,science,type,priority) values (52,0,'Temperature - in situ','PHYSICS','LEFT',10);
insert into option_choice (id,version,name,science,type,priority) values (53,0,'Stress/strain - in situ','PHYSICS','LEFT',11);
insert into option_choice (id,version,name,science,type,priority) values (54,0,'Reaction kinetics - in situ','PHYSICS','LEFT',12);
insert into option_choice (id,version,name,science,type,priority) values (55,0,'Electronic Properties','PHYSICS','LEFT',13);
insert into option_choice (id,version,name,science,type,priority) values (56,0,'Mechanical Properties','PHYSICS','LEFT',14);
insert into option_choice (id,version,name,science,type,priority) values (57,0,'Magnetic Properties','PHYSICS','LEFT',15);
insert into option_choice (id,version,name,science,type,priority) values (58,0,'Optical Properties','PHYSICS','LEFT',16);
-- contacts
insert into contact (id, version, contact_position, title, technique_contact, location_id, telephone, email, name) values (1,0,'A/Professor', 'Dr', 1, 1, '02 8888 9000', 'test1@sydney.edu.au', 'Test One');
insert into contact (id, version, contact_position, title, technique_contact, location_id, telephone, email, name) values (2,0,'A/Professor', 'Dr', 1, 1, '02 8888 9000', 'test2@sydney.edu.au', 'Test Two');
insert into contact (id, version, contact_position, title, technique_contact, location_id, telephone, email, name) values (3,0,'Professor', 'Dr', 1, 2, '02 7777 8000', 'test3@unsw.edu.au', 'Test Three');
insert into contact (id, version, contact_position, title, technique_contact, location_id, telephone, email, name) values (4,0,'Engineer', '', 0, 1, '02 8888 9100', 'test4@sydney.edu.au', 'Test Four');
-- contacts for techniques
insert into technique_contact (technique_contacts_id, contact_id) values (3, 1);
insert into technique_contact (technique_contacts_id, contact_id) values (3, 3);
insert into technique_contact (technique_contacts_id, contact_id) values (2, 3);
