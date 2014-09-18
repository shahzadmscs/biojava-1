/**
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Jul 2, 2012
 * Created by Andreas Prlic
 *
 * @since 3.0.2
 */
package demo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.util.ConcurrencyTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class CookbookMSA {

	private final static Logger logger = LoggerFactory.getLogger(CookbookMSA.class);

    public static void main(String[] args) {
        String[] ids = new String[] {"Q21691", "A8WS47", "O48771"};
        try {
            multipleSequenceAlignment(ids);
        } catch (Exception e){
            logger.error("Exception: ", e);
        }
    }
 
    private static void multipleSequenceAlignment(String[] ids) throws Exception {
        List<ProteinSequence> lst = new ArrayList<ProteinSequence>();
        for (String id : ids) {
            lst.add(getSequenceForId(id));
        }
        Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(lst);
        logger.info("Clustalw:{}{}", System.getProperty("line.separator"), profile);
        
        ConcurrencyTools.shutdown();
    }
 
    private static ProteinSequence getSequenceForId(String uniProtId) throws Exception {
    	URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
    	logger.info("Getting Sequence from URL: {}", uniprotFasta);
        
    	ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(uniprotFasta.openStream()).get(uniProtId);
    	logger.info("id : {} {}{}{}", uniProtId, seq, System.getProperty("line.separator"), seq.getOriginalHeader());
    	
    	return seq;
    }
 
}