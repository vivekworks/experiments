package com.svn;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Spliterator;

public class Access {
    String siteName;
    PrintWriter writer;
    public void launch(String url, String siteName) {
        System.out.println(siteName);
        this.siteName=siteName;
        String URL = url + siteName + "/";
        String user = "";
        String password = "";
        SVNRepository svnRepository = null;
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        try {
            svnRepository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(URL));
            ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(user, password);
            svnRepository.setAuthenticationManager(authenticationManager);
            System.out.println(siteName+" site logged in");
            writer = getWriter();
            if(writer != null) {
                System.out.println("File writing begins");
                listEntries(svnRepository, "");
                writer.flush();
                writer.close();
            }
            System.out.println("Writing completes");
        } catch (Exception e) {
            System.out.println("Error in "+siteName+" ; "+e.getMessage());
            e.printStackTrace();
        } finally {
            if(svnRepository != null){
                svnRepository.closeSession();
            }
        }
    }

    private void listEntries(SVNRepository svnRepository, String path) {
        try {
            Collection entries = svnRepository.getDir(path, -1, null, (Collection) null);
            Spliterator spliterator = entries.spliterator();
            spliterator.forEachRemaining((n) -> {
                SVNDirEntry entry = (SVNDirEntry) n;
                writer.println(entry.getName()+" : "+entry.getSize());
                if (entry.getKind() == SVNNodeKind.DIR) {
                    listEntries(svnRepository, path.equals("") ? entry.getName() : path + "/" + entry.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PrintWriter getWriter(){
        try {
            return new PrintWriter(new BufferedWriter(new FileWriter(siteName + ".txt")));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
