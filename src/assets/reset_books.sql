-- Elimino tutte le righe dalla tabella book
DELETE FROM biblio.book;

-- Resetto la sequenza auto incrementale per la tabella book
DELETE FROM sqlite_sequence WHERE name='book';


DELETE FROM biblio.user;
DELETE FROM sqlite_sequence WHERE name='user';


DELETE FROM biblio.loan;
DELETE FROM sqlite_sequence WHERE name='loan';