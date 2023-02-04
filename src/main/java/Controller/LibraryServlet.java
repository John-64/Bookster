package Controller;

import Model.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LibraryServlet", value = "/LibraryServlet")
public class LibraryServlet extends HttpServlet {
    private int index;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String[] actionSplit=action.split(",");
        action=actionSplit[0];
        String temp=actionSplit[1];
        String[]indexSplit=temp.split("=");
        index=Integer.parseInt(indexSplit[1]);

        if (action != null) {
            if (action.equalsIgnoreCase("libreria")) {
                doGetAddToLib(request, response);
            }else if(action.equalsIgnoreCase("preferiti")){
                doGetAddFavourite(request,response);
            }
        }
    }

    protected void doGetAddToLib(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Book> list = (List<Book>) session.getAttribute("bookList");


        if (session.getAttribute("libraryList") == null) {
            List<Book> librayList = new ArrayList<Book>();
            for (int i = 0; i < list.size(); i++) {
                if (i == index) {
                    librayList.add(list.get(i));

                    session.setAttribute("libraryList", librayList);
                }
            }
        }else {
            List<Book> libraryList = (List<Book>) session.getAttribute("libraryList");
            String isbn=null;
                for(int j=0;j< list.size();j++){
                    if(j==index){
                        isbn=list.get(j).getIsbn();
                    }
             }
                int indexList = isExisting(isbn, libraryList);

                    if(indexList==-1) {
                        for (int j = 0; j < list.size(); j++) {
                            if (j == index) {
                                libraryList.add(list.get(j));

                            }
                        }
                    }

            session.setAttribute("libraryList", libraryList);
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("libraryPage.jsp");
        dispatcher.forward(request, response);
    }

    private int isExisting(String isbn,List<Book> libraryList){
        if(libraryList!=null) {
            for (int i = 0; i < libraryList.size(); i++) {
                if (libraryList.get(i).getIsbn().equalsIgnoreCase(isbn)) {
                    return i;
                }
            }
        }
        return -1;
    }

    protected void doGetAddFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Book> list = (List<Book>) session.getAttribute("bookList");


        if (session.getAttribute("libraryFavourite") == null) {
            List<Book> libraryFavourite = new ArrayList<Book>();
            for (int i = 0; i < list.size(); i++) {
                if (i == index) {
                    libraryFavourite.add(list.get(i));

                    session.setAttribute("libraryFavourite", libraryFavourite);
                }
            }
        }else {
            List<Book> libraryFavourite = (List<Book>) session.getAttribute("libraryFavourite");
            String isbn=null;
            for(int j=0;j< list.size();j++){
                if(j==index){
                    isbn=list.get(j).getIsbn();
                }
            }
            int indexList = isExisting(isbn, libraryFavourite);

            if(indexList==-1) {
                for (int j = 0; j < list.size(); j++) {
                    if (j == index) {
                        libraryFavourite.add(list.get(j));

                    }
                }
            }

            session.setAttribute("libraryFavourite", libraryFavourite);
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("libraryPage.jsp");
        dispatcher.forward(request, response);
    }
}