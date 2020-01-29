package sample;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewFileControllerTest {

    @org.junit.jupiter.api.Test
    void TestpressButtonSaveInEdit() {
        StringWriter writer = new StringWriter();

        String numsteps = "1000";
        String newcoordinates = "D:\\Pobrania\\NAMD_2.13_Win64-multicore\\" +
                "NAMD_2.13_Win64-multicore\\AlaninTest\\alanin.pdb";
        String newstructure = "D:\\Pobrania\\NAMD_2.13_Win64-multicore\\" +
                "NAMD_2.13_Win64-multicore\\AlaninTest\\alanin.psf";
        String newparameters = "D:\\Pobrania\\NAMD_2.13_Win64-multicore\\" +
                "NAMD_2.13_Win64-multicore\\AlaninTest\\alanin.params";
        String exclude = "scaled1-4";
        String outputname = "/tmp/alanin";
        String oneOfThree = "310";
        String additionalParameters =
                "1-4scaling          1.0\n" +
                "cutoff              12.0\n" +
                "switching           on\n" +
                "switchdist          10.0\n" +
                "pairlistdist        14.0";

        newcoordinates = newcoordinates.replace('\\', '/');
        newstructure = newstructure.replace('\\', '/');
        newparameters = newparameters.replace('\\', '/');
        saveFile(Arrays.asList("#NAMD configuration file mad in " +
                        "'NAMD Configuration Fie Creator'" +
                        "\n\n#Required\n",
                "\nnumsteps            " +numsteps,
                "\ncoordinates         " +newcoordinates,
                "\nstructure           " + newstructure,
                "\nparameters          " + newparameters,
                "\nexclude             " + exclude,
                "\noutputname          " + outputname,
                "\n" + oneOfThree + "\n",
                "\n\n#Additional Parameters \n" + additionalParameters), writer);


        assertEquals("#NAMD configuration file mad in 'NAMD Configuration Fie Creator'" +
                "\n\n#Required\n"
                        + "\nnumsteps            " + "1000"
                        + "\ncoordinates         " + "D:/Pobrania/NAMD_2.13_Win64-multicore" +
                "/NAMD_2.13_Win64-multicore/AlaninTest/alanin.pdb"
                        + "\nstructure           " + "D:/Pobrania/NAMD_2.13_Win64-multicore" +
                "/NAMD_2.13_Win64-multicore/AlaninTest/alanin.psf"
                        + "\nparameters          " + "D:/Pobrania/NAMD_2.13_Win64-multicore" +
                "/NAMD_2.13_Win64-multicore/AlaninTest/alanin.params"
                        + "\nexclude             " + "scaled1-4"
                        + "\noutputname          " + "/tmp/alanin"
                        + "\n" + "310" + "\n"
                        + "\n\n#Additional Parameters \n"
                        + "1-4scaling          1.0\n"
                        + "cutoff              12.0\n"
                        + "switching           on\n"
                        + "switchdist          10.0\n"
                        + "pairlistdist        14.0", writer.toString());

        }

    static void saveFile(List<String> contents, Writer writer){
        PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
        for(String data : contents){
            pw.print(data);
        }

        pw.flush();
    }

    @org.junit.jupiter.api.Test
    void TestpressHyperlink(){
        File resourcesDirectory = new File("sample/custom.css");
        String Testlink = resourcesDirectory.toString();

        assertEquals("sample\\custom.css", Testlink);

    }
    }




