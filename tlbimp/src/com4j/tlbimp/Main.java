package com4j.tlbimp;

import com4j.COM4J;
import com4j.Holder;
import com4j.ComException;
import com4j.tlbimp.def.ITypeLib;
import com4j.tlbimp.def.ITypeInfo;
import com4j.tlbimp.def.IWTypeLib;
import com4j.tlbimp.def.IWType;
import com4j.tlbimp.def.TypeKind;
import com4j.tlbimp.def.IWDispInterface;
import com4j.tlbimp.def.IWMethod;

import java.io.File;
import java.io.IOException;

/**
 * @author Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File typeLibFileName = new File(args[0]);
        IWTypeLib tlb = COM4J.loadTypeLibrary(typeLibFileName).queryInterface(IWTypeLib.class);
        new Generator(new File(".")).generate(tlb);
//        dump(tlb);
    }

    private static void dump(IWTypeLib tlb) {
        System.out.println(tlb.count());
        System.out.println(tlb.getName());
        System.out.println(tlb.getHelpString());

        int len = tlb.count();
        for( int i=0; i<len; i++ ) {
            IWType t = tlb.getType(i);
            System.out.println(t.getName());
            System.out.println(t.getKind());
            if(t.getKind()==TypeKind.DISPATCH) {
                IWDispInterface t2 = t.queryInterface(IWDispInterface.class);
                System.out.println(t2.getGUID());
                System.out.println("# of methods: "+t2.countMethods());
                for( int j=0; j<t2.countMethods(); j++ ) {
                    IWMethod m = t2.getMethod(j);
                    System.out.println("  "+m.getKind()+" "+m.getName());
                    m.release();
                }
                t2.release();
            }
            System.out.println(t.getHelpString());
            System.out.println();
            t.release();
        }
    }


//        ITypeLib tlb = COM4J.loadTypeLibrary(typeLibFileName).queryInterface(ITypeLib.class);
//
//        Holder<String> name = new Holder<String>();
//        Holder<String> docstr = new Holder<String>();
//
//        tlb.getDocumentation(0,name,docstr,null,null);
//        System.out.println(name.value+" : "+docstr.value);
//
//        for( int i=0; i<tlb.getTypeInfoCount(); i++ ) {
//            ITypeInfo ti = tlb.getTypeInfo(i);
//            try {
//                ti.getDocumentation(-1/*MEMBERID_NIL*/,name,docstr,null,null);
//                System.out.println(name.value+" : "+docstr.value);
//            } catch( ComException e ) {
//                System.out.println("N/A "+e.getMessage());
//            }
//
//            ti.release();
//        }
//        tlb.release();
//    }
}
