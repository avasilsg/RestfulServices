package assignment3.Interfaces.Model.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.Artefacts;

public class ArtefactResponder {

	private void printReturnToStudent(StringBuffer buffer) {
		buffer.append("<hr><a href='/'>Return to student table</a>");
	}
	
	/**
	 * process add request from the server
	 * @param buffer
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processAddRequest(Artefacts artefact) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		try {

			Artefacts newArtf = new Artefacts();
			
			Vector<Artefacts> all_artf = getAllArtefactInfo();
			newArtf = all_artf.get(all_artf.size() - 1);
			
			newArtf.setArtefactsID(newArtf.getArtefactsID() + 1);
			if (newArtf.getArtefactsID() == 0) {
				throw new IllegalArgumentException("artefact id is not specified.");
			}
			
			newArtf.setTopicID(artefact.getTopicID());
			newArtf.setArtefacts(artefact.getArtefacts());
			
			DBCreator db = new DBCreator();
			try {
				db.addArtefact(newArtf);
			} finally {
				db.close();
			}
			buffer.append("Artefact was added.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
		printReturnToStudent(buffer);
	}
	
	/**
	 * update an artefact's information with a specific ID.
	 * @param buffer
	 * @param artefact
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processEditRequest(Artefacts artefact) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		try {
			
            Map<String, Object> values = new HashMap<String, Object>();

            values.put("topic_id", artefact.getTopicID());
			values.put("refs", artefact.getArtefacts());
			
			DBCreator db = new DBCreator();
			try {
				db.updateArtefact(artefact.getArtefactsID(), values);
			} finally {
				db.close();
			}
			buffer.append("artefact was updated.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
	}

	
	public Artefacts findArtefacts(final long artefactId) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		Artefacts artefact = new Artefacts();
		DBCreator db = new DBCreator();
		try {
			artefact = db.getArtefact(artefactId);
		} finally {
			db.close();
		}
		if (artefact == null) {
			buffer.append("Artefact with ID '");
			buffer.append(artefactId);
			buffer.append("' is not found.");
			printReturnToStudent(buffer);
			return null;
		}
		return artefact;
	}
	
	/**
	 * get all the artefacts' information
	 * @return
	 * @throws SqlJetException
	 */
	public Vector<Artefacts> getAllArtefactInfo () throws SqlJetException {
		
		Vector<Artefacts> artfVector = new Vector<Artefacts>();
		
		DBCreator db = new DBCreator();
		
		try {
			db.beginTransaction(false);
			try {
				ISqlJetCursor cursor;
				cursor = db.getAllArtefact();
				try {
					while (!cursor.eof()) {
						Artefacts artefact = new Artefacts();
						artefact.read(cursor);
						artfVector.add(artefact);
			            cursor.next();
					}
					
				} finally {
					cursor.close();
				}
				//printShowUsers(db, buffer);
			} finally {
				db.commit();
			}
		} finally {
			db.close();
		}
		
		return artfVector;
	}
	
	/**
	 * remove an artefact with a specific id.
	 * @param buffer
	 * @param artefactId
	 * @throws SqlJetException
	 */
	public void removeArtefact(final long artefactId) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		if (artefactId != 0) {
			DBCreator db = new DBCreator();
			try {
				db.removeArtefact(artefactId);
			} finally {
				db.close();
			}
			buffer.append("artfact was removed.");
			printReturnToStudent(buffer);
		}
		
	}
	
	//Vector<String> getReferences(String topicID)
	
	public String getAllReferencesWithTopicID (long topicID) throws SqlJetException {
		
		Vector<Artefacts> allArtf = getAllArtefactInfo();
		Artefacts curArtf = new Artefacts();
		String refs = new String();
		
		for (int index = 0; index < allArtf.size(); index ++) {
			
			curArtf = allArtf.get(index);
			
			if (curArtf.getTopicID() == topicID) {
                 
				refs = curArtf.getArtefacts();
			}
		}
		
		return refs;
	}
}
