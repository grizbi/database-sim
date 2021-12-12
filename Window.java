import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Window extends JFrame {

    private JButton Search_Button, Add_Buton, Delete_Button, ShowDatabase, submit;
    private FlowLayout Layout;
    private JLabel label1, label2, prompt, results;
    private Iterator<String> itr;
    private File myFile;
    private Scanner scan;
    private ArrayList<String> list;
    private boolean label1Visible;
    private JPanel panel1;
    private JTextField add_text;
    private boolean add_movie_visible, isSearchVisible, isDeleteSubmit;






    Window() {
        super("DataBase");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setSize(new Dimension(dim.width / 4, dim.height / 4));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Layout = new FlowLayout();
        setLayout(Layout);

        Search_Button = new JButton("Search");
        Add_Buton = new JButton("Add record");
        Delete_Button = new JButton("Delete");
        ShowDatabase = new JButton("Show Database");


        add(ShowDatabase);
        add(Search_Button);
        add(Add_Buton);
        add(Delete_Button);

        isDeleteSubmit = false;
        results = new JLabel();
        submit = new JButton("Submit");
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5,4));
        add(panel1);
        label1 = new JLabel();
        add(label1);
        label1.setVisible(false);
        prompt = new JLabel("Inaproprriate length of title");
        add_text = new JTextField(18);
        add(add_text);
        add_text.setVisible(false);
        add(results);

        isSearchVisible = false;
        list = new ArrayList<>();
        label1Visible = false;
        add_movie_visible = false;

        try {
            myFile = new File("movies.txt");
            scan = new Scanner(myFile);
            scan = new Scanner(myFile);
            while (scan.hasNextLine()) {
                String Data = scan.nextLine();
                list.add(Data);
            }
        }



        catch(FileNotFoundException ex)
        {
            System.out.println("File not found");
        }





        Search_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Search")) {
                    results.setVisible(false);
                    add_text.setText("");
                    add_movie_visible = false;
                    prompt.setVisible(false);
                    submit.setVisible(true);
                    label1.setText("Pass title of the movie to search for: ");
                    if (label1Visible) {
                        panel1.removeAll();
                        label1Visible = false;
                    }
                    add_text.setVisible(true);
                    label1.setVisible(true);
                    add(submit);
                    repaint();
                    revalidate();
                    isSearchVisible = true;
                    isDeleteSubmit = false;
                    add_movie_visible = false;
                }


            }
        });



        Add_Buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Add record"))
                {
                    add_text.setText("");
                    add_text.setVisible(true);
                    prompt.setVisible(false);
                    submit.setVisible(true);
                    results.setVisible(false);
                    if(label1Visible) {
                        panel1.removeAll();
                        label1Visible = false;
                    }
                    label1.setText("Pass title of the movie:");
                    label1.setVisible(true);

                    add(submit);
                    repaint();
                    revalidate();

                }
                add_movie_visible = true;
                isSearchVisible = false;
                isDeleteSubmit = false;



            }
        });

        Delete_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getActionCommand().equals("Delete"))
                {
                    add_text.setText("");
                    add_text.setVisible(true);
                    prompt.setVisible(false);
                    submit.setVisible(true);
                    results.setVisible(false);
                    if(label1Visible) {
                        panel1.removeAll();
                        label1Visible = false;
                    }
                    label1.setText("Pass title of the movie you want to delete:");


                    label1.setVisible(true);
                    isDeleteSubmit = true;
                    add_movie_visible = false;
                    isSearchVisible = false;

                    add(submit);
                    repaint();
                    revalidate();

                }




            }
        });
        
        
        
        
        


        ShowDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if ((ae.getActionCommand().equals("Show Database")))
                {
                    submit.setVisible(false);
                    label1.setVisible(false);
                    add_movie_visible = false;
                    prompt.setVisible(false);
                    results.setVisible(false);
                    add_text.setVisible(false);

                    itr = list.iterator();
                    while(itr.hasNext() && !label1Visible)
                    {

                        label2 = new JLabel(itr.next(),JLabel.CENTER);
                        panel1.add(label2);
                        revalidate();
                        repaint();

                    }
                    label1Visible = true;
                    add_movie_visible = false;
                    isSearchVisible = false;
                    isDeleteSubmit = false;


                }


            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if ((ae.getActionCommand().equals("Submit")) && add_movie_visible) {
                    try {
                        String record1 = add_text.getText();
                        if (record1.length() > 15 || record1.length() == 0)
                            throw new OutOfRange("Inappropriate length of title");
                        list.add(record1);
                        prompt.setText("Successfully added record");
                        prompt.setVisible(true);
                        submit.setVisible(false);
                        add(prompt);
                        repaint();
                        revalidate();



                    }

                   catch(OutOfRange ex)
                    {
                        prompt.setText(ex.errorMessage);
                        add(prompt);
                        repaint();
                        revalidate();
                        prompt.setVisible(true);
                        submit.setVisible(false);

                    }
                }
                else if ((ae.getActionCommand().equals("Submit")) && isSearchVisible)
                {
                    try {
                        int counter = 0;
                        itr = list.iterator();
                        while (itr.hasNext()) {
                            if (itr.next().equals(add_text.getText()))
                                counter++;
                            if (add_text.getText().length()==0 || add_text.getText().length()>=15)
                                throw new OutOfRange("Inappropriate length of title");
                        }

                        results.setText("Matchings phrases: " + counter);
                        results.setVisible(true);
                        submit.setVisible(false);
                        repaint();
                        revalidate();
                    }

                    catch (OutOfRange ex)
                    {
                        results.setText(ex.errorMessage);
                        results.setVisible(true);
                        submit.setVisible(false);

                    }




                }

                else if ((ae.getActionCommand().equals("Submit")) && isDeleteSubmit)
                {
                    try {
                        boolean found = false;
                        submit.setVisible(true);


                        itr = list.iterator();
                        while (itr.hasNext()) {
                            if (itr.next().equals(add_text.getText())) {
                                found = true;
                                itr.remove();
                                results.setText("Record deleted successfully");
                                submit.setVisible(false);
                                results.setVisible(true);
                            }
                            if (add_text.getText().length()==0 || add_text.getText().length()>=15)
                                throw new OutOfRange("Inappropriate length of title");


                        }

                        if (!found) {
                            results.setText("No record in data base");
                            submit.setVisible(false);
                            results.setVisible(true);
                            repaint();
                            revalidate();
                        }

                        repaint();
                        revalidate();
                    }

                    catch (OutOfRange ex)
                    {
                        results.setText(ex.errorMessage);
                        results.setVisible(true);
                        submit.setVisible(false);

                    }
                }




            }
        });



    }

    public void Search(String el)
    {
        itr = this.list.iterator();
        while(itr.hasNext())
        {
            if(itr.next().equals(el))
                System.out.println("Znaleziono"+el);

        }

    }

    class OutOfRange extends Exception
    {
        private String errorMessage;
        public OutOfRange(String errorMessage)
        {
            this.errorMessage = errorMessage;
        }
    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();

            }
        });


    }

}