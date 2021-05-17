package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Editor {
    private ArrayList<Author> authors;
    private ArrayList<Book> books;

    private DocumentBuilderFactory documentBuilderFactory = null;
    private DocumentBuilder documentBuilder = null;
    private Document document = null;
    private String fileName = null;
    private Bibliotek bibliotek = null;


    public static ArrayList<Author> getInfo(Document document)  {
        NodeList authorsList = document.getElementsByTagName("author");
        ArrayList<Author> allAuthors = new ArrayList<>();

        for (int i = 0; i < authorsList.getLength(); i++){
            if (authorsList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element authorElement = (Element) authorsList.item(i);

                Author newAuthor = new Author();
                newAuthor.setName(authorElement.getAttribute("name"));

                NodeList bookNodes = authorElement.getChildNodes();
                for (int j = 0; j < bookNodes.getLength(); j++) {
                    if (bookNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNodes.item(j);

                        String name = bookElement.getAttribute("name");
                        String pages = bookElement.getAttribute("pages");
                        Book newBook = new Book();
                        newBook.setName(name);
                        newBook.setPages(pages);
                        newAuthor.addBook(newBook);
                    }
                }

                allAuthors.add(newAuthor);
            }
        }

        return allAuthors;
    }

    public static void showInfo(ArrayList<Author> authors) {
        for (Author author : authors) {
            System.out.println(author.getName() + ":");
            for (int j = 0; j < author.getBook().size(); j++) {
                System.out.println(" " + author.getBook().get(j).getName() + " " + author.getBook().get(j).getPages());
            }
        }
    }


    // Получить количество авторов
    public static int countAuthors(ArrayList<Author> authors)
    {
        return authors.size();
    }

    public static void removeBook(ArrayList<Author> authors, String author, String book) {
        for (Author value : authors) {
            if (value.getName().equals(author)) {
                for (int j = 0; j < value.getBook().size(); j++) {
                    if (value.getBook().get(j).getName().equals(book)) {
                        value.removeBook(value.getBook().get(j));
                    }
                }
            }
        }
    }

        // Удалить автора
    public static void removeAuthor(ArrayList<Author> authors, String author)
    {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getName().equals(author)) {
                for (int j = 0; j < authors.get(i).getBook().size(); j++) {
                    removeBook(authors, author, authors.get(i).getBook().get(j).getName());
                }
                authors.remove(authors.get(i));
            }
        }
    }

    public static void addBook(ArrayList<Author> authors, String author, String book, String pages)
    {
        for (Author value : authors) {
            if (value.getName().equals(author)) {
                Book newBook = new Book();
                newBook.setName(book);
                newBook.setPages(pages);
                value.addBook(newBook);
            }
        }
    }

    public static void addAuthor(ArrayList<Author> authors, String author) {
        Author newAuthor = new Author();
        newAuthor.setName(author);
        authors.add(newAuthor);
    }

    public static void createXML(ArrayList<Author> authors) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();

        Element list = doc.createElement("list");
        Element auth = doc.createElement("authors");
        for (Author author : authors) {
            Element newAuthor = doc.createElement("author");
            newAuthor.setAttribute("name", author.getName());

            for (Book book : author.getBook()) {
                Element newSoftware = doc.createElement("Software");
                newSoftware.setAttribute("book", book.getName());

                Element pages = doc.createElement("pages");
                pages.setTextContent(book.getPages());

                newSoftware.appendChild(pages);
                newAuthor.appendChild(newSoftware);
            }
            auth.appendChild(newAuthor);
        }
        list.appendChild(auth);
        doc.appendChild(list);

        File file = new File("C:\\Users\\user\\IdeaProjects\\Module2\\Bibliotek.xml");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("Bibliotek.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        ArrayList<Author> authors = getInfo(document);
        System.out.println("-----------Print all-------------");
        showInfo(authors);
        System.out.println("Count authors: "+countAuthors(authors));
        System.out.println("-----------Add author and book-------------");
        addAuthor(authors, "Panas Myrnyi");
        addBook(authors, "Panas Myrnyi", "Voly", "350");
        showInfo(authors);
        System.out.println("Count authors: "+countAuthors(authors));
        System.out.println("-----------Add book-------------");
        addBook(authors, "Taras Shevchenko", "Zapovit", "10");
        showInfo(authors);
        System.out.println("Count authors: "+countAuthors(authors));
        System.out.println("----------Remove book and author--------------");
        removeBook(authors, "Taras Shevchenko", "Son");
        removeAuthor(authors, "Ivan Franko");
        showInfo(authors);
        System.out.println("Count authors: "+countAuthors(authors));

    }

}
