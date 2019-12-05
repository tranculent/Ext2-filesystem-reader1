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
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">>>");
            String answer = scanner.nextLine();

            if (answer.equals("ls")) 
                for (int i = 0; i < fileInfoArray.length; i++)
                    System.out.println(inodes[fileInfoArray[i].getInodeNum() - 1] + " " + fileInfoArray[i].getName());
            
            for (int i = 0; i < fileInfoArray.length; i++) {
                if (answer.equals("cd " + fileInfoArray[i].getName())) {
                    Inode inodeToBeSearched = inodes[fileInfoArray[i].getInodeNum() - 1];
                    for (int j = 0; j < inodeToBeSearched.getDataBlockPointers().length; j++) {
                        if (inodeToBeSearched.getDataBlockPointers()[j] != 0) {
                            Inode contentToBeRead = new Inode(inodeToBeSearched.getDataBlockPointers()[j] * 1024, file);

                            fileInfos.clear();

                            if (!contentToBeRead.isFile()) {
                                offset = 0;
                                while (offset < 1024) {
                                    FileInfo fInfo = new FileInfo(offset + inodeToBeSearched.getDataBlockPointers()[j] * 1024, file);
                                    fileInfos.add(fInfo);
                                    offset += fInfo.getLength();
                                    // System.out.println("Inode: " + inodes[fInfo.getInodeNum() - 1] + " " + fInfo.getName() + ", length: " + fInfo.getLength());
                                }
                        
                                FileInfo[] innerFilesArray = new FileInfo[fileInfos.size()];
                                innerFilesArray = fileInfos.toArray(innerFilesArray);
                        
                                for (int k = 0; k < innerFilesArray.length; k++)
                                    System.out.println(inodes[innerFilesArray[k].getInodeNum() - 1] + " " + innerFilesArray[k].getName());
                            }
                            else
                                System.out.println("nope:");
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