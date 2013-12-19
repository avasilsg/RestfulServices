package assignment3.Interfaces.Model.DataBase;

import java.io.File;
import java.util.Map;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import assignment3.Interfaces.Model.Artefacts;
import assignment3.Interfaces.Model.ChatInformation;
import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;

public class DBCreator {
	
	private static final int VERSION = 6;
	private static final String FILE_NAME = "projmanage.db";
    private SqlJetDb projManageDb; 
    
    
    public DBCreator () throws SqlJetException {
    	
    	projManageDb = SqlJetDb.open(new File(FILE_NAME), true);
    	
		upgrade(VERSION);
	}
        
    public void close () throws SqlJetException {

        projManageDb.close();
        projManageDb = null;
    }
    
	public void commit() throws SqlJetException {
		projManageDb.commit();
	}
    
	public void beginTransaction (boolean write) throws SqlJetException {

		projManageDb.beginTransaction( write ? SqlJetTransactionMode.WRITE : SqlJetTransactionMode.READ_ONLY);
	}
	
	private void upgrade(int targetVersion) throws SqlJetException {

        if (targetVersion < 1) {
            return;
        }
         
        if (getVersion() < 1) {
        	
            projManageDb.runWriteTransaction(new ISqlJetTransaction() {
        		
                public Object run(SqlJetDb db) throws SqlJetException {
                	projManageDb.createTable("create table users (user_id  integer primary key, role text," +
                			" name text not null, personName text not null, address text, " +
                			"email text, password text, researchInterests text)"); 
     
                	projManageDb.getOptions().setUserVersion(1);
                	prefillUsers();
                	//System.out.println("test");
                	
                    return null;
                }
            });
        }
        
        if (targetVersion < 2) {
            return;
        }
        
        if (getVersion () < 2) {

            projManageDb.runWriteTransaction(new ISqlJetTransaction(){
        		
                public Object run(SqlJetDb db) throws SqlJetException {
                
                	projManageDb.createTable("create table topics(topic_id integer primary key, " +
                			"proposer integer, title text not null, description text, " +
                			"assigned_to integer, supervisor_id integer, accepted bit, pre_exp bit)"); 
                	
                	projManageDb.getOptions().setUserVersion(2);
                	prefillTopics();
                	
                    return null;
                }
            });      	
        }

        if (targetVersion < 3) {
            return;
        }
        
        if (getVersion () < 3) {

            projManageDb.runWriteTransaction(new ISqlJetTransaction(){
        		
                public Object run(SqlJetDb db) throws SqlJetException {
                
                	projManageDb.createTable("create table artefacts(artefact_id integer primary key, " +
                			"topic_id integer, refs text)"); 
                	
                	projManageDb.getOptions().setUserVersion(3);
                	prefillArtefact();
                	
                    return null;
                }
            });      	
        }
        
        if (targetVersion < 4) {
            return;
        }
        
        if (getVersion () < 4) {

            projManageDb.runWriteTransaction(new ISqlJetTransaction(){
        		
                public Object run(SqlJetDb db) throws SqlJetException {
                
                    projManageDb.createTable("create table interests(interest_id integer primary key, " +
                            "topic_id integer, student_id integer)"); 
                	
                	projManageDb.getOptions().setUserVersion(4);
                	prefillInterests();
                	
                    return null;
                }
            });      	
        }
        
        if (targetVersion < 5) {
            return;
        }
        
        if (getVersion () < 5) {

            projManageDb.runWriteTransaction(new ISqlJetTransaction(){
        		
                public Object run(SqlJetDb db) throws SqlJetException {
                
                	projManageDb.createTable("create table chats(infor_id integer primary key, "
                	        + "topic_id integer, chat_infor text)"); 
                	
                	projManageDb.getOptions().setUserVersion(5);
                	prefillChatInfor();
                	
                    return null;
                }
            });      	
        }
        
    	if (targetVersion > 6) {
    		
			throw new IllegalArgumentException("Unsupported version: " + targetVersion);
		}
	}
	
	public int getVersion () throws SqlJetException {

        return projManageDb.getOptions().getUserVersion();
	}
	
	private void prefillUsers () throws SqlJetException {
		
		addUser(new User(12341, "Faculty", "Harry", "Lee Jordan", "1 South Street", "xxx", 
	    		"123", "MIS"));
		addUser(new User(12342, "Faculty", "For", "John", "2 Market Street", 
	    		"aaa", "234", "Hacking NSA"));
		addUser(new User(12343, "Faculty", "Messi", "Didier", "3 Market Street", 
	    		"bbb", "345", "Distributed System"));
		addUser(new User(12344, "Student", "Ronaldo", "Dominique", "4 South Street", 
	    		"ccc", "456", ""));
		addUser(new User(12345, "Student", "Anelia", "Dominique", "4 South Street", 
                "ccc", "456", ""));
		addUser(new User(12346, "Student", "Pres", "Dominique", "4 South Street", 
                "ccc", "456", ""));
		addUser(new User(12347, "Student", "Elly", "Dominique", "4 South Street", 
                "ccc", "456", ""));
	}
	
	public long addUser(final User user) throws SqlJetException {
		
        user.setUserID((Long)projManageDb.runWriteTransaction(new ISqlJetTransaction() {

            public Object run(SqlJetDb db) throws SqlJetException {

                return projManageDb.getTable("users").insert(user.getUserID(), user.getRole(), 
                        user.getName(), user.getPersonName(), user.getAddress(), user.getEmail(),
                        user.getPasswordString(), user.getResInterest());
            }
        }));
        
        return user.getUserID();
	} 
	
	public void updateUser(final long userID, final Map<String, Object> values) throws SqlJetException {
		
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("users").open();
				try {
					if (cursor.goTo(userID)) {
						cursor.updateByFieldNames(values);
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public User getUser(final long userID) throws SqlJetException {
		return (User) projManageDb.runReadTransaction(new ISqlJetTransaction() {
			
			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = db.getTable("users").open();
				try {
					if (cursor.goTo(userID)) {
						User user = new User();
						user.read(cursor);
						return user;
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
		
	public ISqlJetCursor getAllUser() throws SqlJetException {
		return projManageDb.getTable("users").open();
	}
	
	/**
	 * @throws SqlJetException
	 */
	private void prefillTopics () throws SqlJetException {
		
		addTopic(new Topic(81001, 12341, "MIS development", "0.11 JC Building", 
				54325, 12341, false, false));
		addTopic(new Topic(81002, 12342, "Hacking NSA system", "0.12 JC Building", 
				54326, 12342, false, false));
		addTopic(new Topic(81003, 12343, "Landing on the moon", "0.13 JC Building", 
				54327, 12343, true, false));
		addTopic(new Topic(81004, 12344, "Win WWIII", "0.14 JC Building", 
				54328, 12344, false, false));
		addTopic(new Topic(81005, 12344, "Win WWIII", "0.14 JC Building", 
				54328, 12344, false, false));
		
	      addTopic(new Topic(81007, 12341, "MIS development", "0.11 JC Building", 
	                0, 12341, false, false));
	        addTopic(new Topic(81008, 12342, "Hacking NSA system", "Yale", 
	                0, 12342, false, false));
	        addTopic(new Topic(81093, 12343, "Landing on the moon", "0.13 JC Building", 
	                0, 12343, true, false));
	        addTopic(new Topic(5284, 12344, "Win WWIII", "0.14 JC Building", 
	                0, 12344, false, false));
	        addTopic(new Topic(3698, 12344, "Win WWIII", "0.14 JC Building", 
	                0, 12344, false, false));
	        
	        addTopic(new Topic(8125, 12341, "MIS development", "Harvard Business School", 
	                0, 12341, false, false));
	        addTopic(new Topic(8545, 12342, "Hacking NSA system", "0.12 JC Building", 
	                0, 12342, false, false));
	        addTopic(new Topic(6587, 12343, "Landing on the moon", "0.13 JC Building", 
	                0, 12343, true, false));
	
	}
	
	public long addTopic(final Topic topic) throws SqlJetException {

	    topic.setTopicID((Long)projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {

				return projManageDb.getTable("topics").insert(topic.getTopicID(), topic.getProposedBy(), 
						topic.getTitle(), topic.getDescription(), topic.getAssignedTo(), 
						topic.getSupervisorID(), topic.isAccepted(), topic.getPreviousExperince());

			}
		}));
	    
	    return topic.getTopicID();
	} 
	
	public void removeTopic(final long topicID) throws SqlJetException {
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("topics").open();
				try {
					if (cursor.goTo(topicID)) {
						cursor.delete();
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public Topic getTopic(final long topicID) throws SqlJetException {
		return (Topic) projManageDb.runReadTransaction(new ISqlJetTransaction() {
			
			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = db.getTable("topics").open();
				try {
					if (cursor.goTo(topicID)) {
						Topic topic = new Topic();
						topic.read(cursor);
						return topic;
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public void updateTopic(final long topicID, final Map<String, Object> values) throws SqlJetException {
		
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("topics").open();
				try {
					if (cursor.goTo(topicID)) {
						cursor.updateByFieldNames(values);
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public ISqlJetCursor getAllTopic() throws SqlJetException {
		return projManageDb.getTable("topics").open();
	}
	
	
	private void prefillArtefact () throws SqlJetException {
		
		addArtefact(new Artefacts(70001, 81001, "Batman"));
		addArtefact(new Artefacts(70002, 81002, "Spiderman"));
		addArtefact(new Artefacts(70003, 81003, "Superman"));
		addArtefact(new Artefacts(70004, 81004, "Ironman"));

	}
	
	public long addArtefact (final Artefacts artefact) throws SqlJetException {
		
		artefact.setArtefactsID((Long)projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {

				return projManageDb.getTable("artefacts").insert(artefact.getArtefactsID(), 
						artefact.getTopicID(), artefact.getArtefacts());
			}
		}));
		
		return artefact.getArtefactsID();
	} 
	
	public Artefacts getArtefact (final long artefactID) throws SqlJetException {
		
		return (Artefacts) projManageDb.runReadTransaction(new ISqlJetTransaction() {
			
			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = db.getTable("artefacts").open();
				try {
					if (cursor.goTo(artefactID)) {
						Artefacts artefact = new Artefacts();
						artefact.read(cursor);
						return artefact;
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public void removeArtefact(final long artefactID) throws SqlJetException {
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("artefacts").open();
				try {
					if (cursor.goTo(artefactID)) {
						cursor.delete();
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public void updateArtefact(final long artefactID, final Map<String, Object> values) throws SqlJetException {
		
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("artefacts").open();
				try {
					if (cursor.goTo(artefactID)) {
						cursor.updateByFieldNames(values);
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public ISqlJetCursor getAllArtefact () throws SqlJetException {
		return projManageDb.getTable("artefacts").open();
	}
	
	
    private void prefillInterests () throws SqlJetException {
		
    	addInterest(new Interest(1, 81001, 130001));
    	addInterest(new Interest(2, 81002, 130002));
    	
    	//removeInterest(81002);
    	
	}
	
	
    public long addInterest (final Interest interest) throws SqlJetException {
        
        interest.setTopicID((Long)projManageDb.runWriteTransaction(new ISqlJetTransaction() {

            public Object run(SqlJetDb db) throws SqlJetException {

                return projManageDb.getTable("interests").insert(interest.getInterestID(), 
                        interest.getTopicID(), interest.getStudentID());
            }
        }));
        
        return interest.getTopicID();
    } 
    
    public Interest getInterest (final long interestID) throws SqlJetException {
        
        return (Interest) projManageDb.runReadTransaction(new ISqlJetTransaction() {
            
            public Object run(SqlJetDb db) throws SqlJetException {
                ISqlJetCursor cursor = db.getTable("interests").open();
                try {
                    if (cursor.goTo(interestID)) {
                        Interest interest = new Interest();
                        interest.read(cursor);
                        return interest;
                    }
                } finally {
                    cursor.close();
                }
                return null;
            }
        });
    }
    
    public void removeInterest (final long interestID) throws SqlJetException {
        projManageDb.runWriteTransaction(new ISqlJetTransaction() {

            public Object run(SqlJetDb db) throws SqlJetException {
                ISqlJetCursor cursor = projManageDb.getTable("interests").open();
                try {
                    if (cursor.goTo(interestID)) {
                        cursor.delete();
                    }
                } finally {
                    cursor.close();
                }
                return null;
            }
        });
    }
    
    public void updateInterest(final long interestID, final Map<String, Object> values) throws SqlJetException {
        
        projManageDb.runWriteTransaction(new ISqlJetTransaction() {

            public Object run(SqlJetDb db) throws SqlJetException {
                ISqlJetCursor cursor = projManageDb.getTable("interests").open();
                try {
                    if (cursor.goTo(interestID)) {
                        //System.out.println("hello");
                        cursor.updateByFieldNames(values);
                    }
                } finally {
                    cursor.close();
                }
                return null;
            }
        });
    }

	
	public ISqlJetCursor getAllInterest () throws SqlJetException {
		return projManageDb.getTable("interests").open();
	}
	
	private void prefillChatInfor () throws SqlJetException {
		
		addChatInfor(new ChatInformation(1, 81001, "Bonjour!"));
		addChatInfor(new ChatInformation(2, 81002, "Ca va?"));
	}
	
	public long addChatInfor (final ChatInformation chatInfor) throws SqlJetException {
		
		chatInfor.setInforID((Long)projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {

				return projManageDb.getTable("chats").insert(chatInfor.getInforID(),
						chatInfor.getChatInfor());
			}
		}));
		
		return chatInfor.getInforID();
	} 
	
	
	public ChatInformation getChatInfor (final long inforID) throws SqlJetException {
		return (ChatInformation) projManageDb.runReadTransaction(new ISqlJetTransaction() {
			
			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = db.getTable("chats").open();
				try {
					if (cursor.goTo(inforID)) {
						ChatInformation chatInfor = new ChatInformation();
						chatInfor.read(cursor);
						return chatInfor;
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public void updateChatInfor(final long inforID, final Map<String, Object> values) throws SqlJetException {
		
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("chats").open();
				try {
					if (cursor.goTo(inforID)) {
						cursor.updateByFieldNames(values);
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public void removeChatInfor(final long inforID) throws SqlJetException {
		projManageDb.runWriteTransaction(new ISqlJetTransaction() {

			public Object run(SqlJetDb db) throws SqlJetException {
				ISqlJetCursor cursor = projManageDb.getTable("chats").open();
				try {
					if (cursor.goTo(inforID)) {
						cursor.delete();
					}
				} finally {
					cursor.close();
				}
				return null;
			}
		});
	}
	
	public ISqlJetCursor getAllChatInfor () throws SqlJetException {
		return projManageDb.getTable("chats").open();
	}

}
