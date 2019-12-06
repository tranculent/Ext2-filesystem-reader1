import java.lang.System;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * Traverses through FileInfo entries
 */
public class Directory {
    private Ext2File file;
    private Inode[] inodes;
   
    private int offset;
    private int fileInfoIndex;
    private ArrayList<FileInfo> fileInfos;

    private FileInfo[] fileInfoArray;

    public Directory(Inode[] inodes, Ext2File file) {
        this.file   = file;
        this.inodes = inodes;
        fileInfoIndex = 0;
        fileInfos = new ArrayList<>();

        offset = 0;

        while (offset < 1024) {
            FileInfo fInfo = new FileInfo(offset + inodes[1].getDataBlockPointers()[0] * 1024, file);
            fileInfos.add(fInfo);
            offset += fInfo.getLength();
        }

        fileInfoArray = new FileInfo[fileInfos.size()];
        fileInfoArray = fileInfos.toArray(fileInfoArray);

        for (int i = 0; i < fileInfoArray.length; i++)
            System.out.println(inodes[fileInfoArray[i].getInodeNum() - 1] + " " + fileInfoArray[i].getName());

       /* TRAVERSE Directories enlisted */

       // FIX lost+found and big-dir - not returning from there, unable to call cd ..l
       while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">>>");
            String answer = scanner.nextLine();
            fileInfos.clear();
            if (answer.equals("ls")) {

                System.out.println("Array in ls: " + fileInfoArray.length);
                for (int i = 0; i < fileInfoArray.length; i++) {
                    if (fileInfoArray[i].getInodeNum() > 0)
                        System.out.println(inodes[fileInfoArray[i].getInodeNum() - 1] + " " + fileInfoArray[i].getName());
                    else 
                        System.out.println(inodes[fileInfoArray[i].getInodeNum()] + " " + fileInfoArray[i].getName());
                }
            }   
            
            if (answer.equals("exit")) break;

            for (int i = 0; i < fileInfoArray.length; i++) {

                if (answer.equals("cd " + fileInfoArray[i].getName()) && !inodes[fileInfoArray[i].getInodeNum() > 0 ? fileInfoArray[i].getInodeNum() - 1 : fileInfoArray[i].getInodeNum()].isFile()) {
                    Inode inodeToBeSearched = inodes[fileInfoArray[i].getInodeNum() > 0 ? fileInfoArray[i].getInodeNum() - 1 : fileInfoArray[i].getInodeNum()];

                    for (int j = 0; j < inodeToBeSearched.getDataBlockPointers().length; j++) {

                        if (inodeToBeSearched.getDataBlockPointers()[j] != 0) {

                            offset = 0;

                            while (offset < 1024) {

                                FileInfo fInfo = new FileInfo(offset + inodeToBeSearched.getDataBlockPointers()[j] * 1024, file);
                                fileInfos.add(fInfo);
                                offset += fInfo.getLength();
                                // System.out.println("File Info: " + fileInfos.size());
                            }                        
                            
                            fileInfoArray = new FileInfo[fileInfos.size()];
                            fileInfoArray = fileInfos.toArray(fileInfoArray);
                    
                            for (int k = 0; k < fileInfoArray.length; k++) {
                                if (fileInfoArray[k].getInodeNum() < 5136) {
                                    if (fileInfoArray[k].getInodeNum() > 0)
                                        System.out.println(inodes[fileInfoArray[k].getInodeNum() - 1] + " " + fileInfoArray[k].getName());
                                    else 
                                        System.out.println(inodes[fileInfoArray[k].getInodeNum()] + " " + fileInfoArray[k].getName());
                                }
                            }
                            
                        }
                    }
                }
                else if (answer.equals("cat " + fileInfoArray[i].getName())) {
                    Inode inodeToBeSearched = inodes[fileInfoArray[i].getInodeNum() > 0 ? fileInfoArray[i].getInodeNum() - 1 : fileInfoArray[i].getInodeNum()];
                    for (int j = 0; j < inodeToBeSearched.getDataBlockPointers().length; j++) {
                        if (inodeToBeSearched.getDataBlockPointers()[j] != 0) {
                            System.out.println(new String(file.read(inodeToBeSearched.getDataBlockPointers()[j] * 1024, 1024)));
                        }
                    }
                    
                }
            }
        }
    }

    public FileInfo[] getFileInfo() {
        return fileInfoArray;
    }
}