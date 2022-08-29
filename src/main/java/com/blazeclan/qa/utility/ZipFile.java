package com.blazeclan.qa.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipFile {

  private final List <String> fileList;

  public ZipFile() {
      fileList = new ArrayList <> ();
  }

  public void zipIt(String zipFile, String sourceFolder) {
      byte[] buffer = new byte[1024];
      String source = new File(sourceFolder).getName();
      FileOutputStream fos;
      ZipOutputStream zos = null;
      try {
    	  fos = new FileOutputStream(zipFile);
          zos = new ZipOutputStream(fos);

          System.out.println("Output to Zip : " + zipFile);
          FileInputStream in = null;

          for (String file: this.fileList) {
              System.out.println("File Added : " + file);
  //--            ZipEntry ze = new ZipEntry(source + File.separator + file);
              ZipEntry ze = new ZipEntry(source + "/" + file);
              zos.putNextEntry(ze);
              try {
   //--               in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                  in = new FileInputStream(sourceFolder + "/" + file);
                  int len;
                  while ((len = in .read(buffer)) > 0) {
                      zos.write(buffer, 0, len);
                  }
              } finally {
                  assert in != null;
                  in.close();
              }
          }

          zos.closeEntry();
          System.out.println("Folder successfully compressed");

      } catch (IOException ex) {
          ex.printStackTrace();
      } finally {
          try {
              assert zos != null;
              zos.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }

  public void generateFileList(File node, String sourceFolder) {
      // add file only
      if (node.isFile()) {
          fileList.add(generateZipEntry(node.toString(), sourceFolder));
      }

      if (node.isDirectory()) {
          String[] subNote = node.list();
          assert subNote != null;
          for (String filename: subNote) {
              generateFileList(new File(node, filename), sourceFolder);
          }
      }
  }

  private String generateZipEntry(String file, String sourceFolder) {
      return file.substring(sourceFolder.length() + 1);
  }
}
