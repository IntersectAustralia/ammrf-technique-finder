import org.tmatesoft.svn.core.wc.*;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.io.*;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;

eventWarStart = { type ->
	println "******************* Retriving SVN revision number *****************"
	def version = "rev?"
	try {
		// initialise SVNKit
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	   
		SVNClientManager clientManager = SVNClientManager.newInstance();
		SVNWCClient wcClient = clientManager.getWCClient();

		// the svnkit equivalent of "svn info"
		File baseFile = new File(basedir);

		println "baseFile = " + baseFile.toString();
		SVNInfo svninfo = wcClient.doInfo(baseFile, SVNRevision.WORKING);

		version = svninfo.getRevision().toString();

	}
	catch (SVNException ex) {
		//something went wrong
		println "**************** SVN exception **************"
		println ex.getMessage();
	}
	println "Setting SVN Revision to: ${version}"
   	File revisionFile = new File("$grailsSettings.baseDir/revision.txt")
	revisionFile.write(version)   
}