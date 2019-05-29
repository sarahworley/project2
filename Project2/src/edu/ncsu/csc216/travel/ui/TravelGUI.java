package edu.ncsu.csc216.travel.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import edu.ncsu.csc216.travel.model.office.*;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;

/** 
 * Wolf Travel graphical user interface.
 * 
 * @author Jo Perry
 */
public class TravelGUI extends JFrame implements ActionListener, Observer {

   /** ID number used for object serialization. */
   private static final long serialVersionUID = 1L;
   /** Title for top of GUI. */
   private static final String APP_TITLE = "Wolf Travel";
    
   // Menu items
   /** Text for the File Menu. */
   private static final String FILE_MENU_TITLE = "File";
   /** Text for the New Issue XML menu item. */
   private static final String NEW_FILE_TITLE = "New";
   /** Text for the Load Issue XML menu item. */
   private static final String LOAD_FILE_TITLE = "Load";
   /** Text for the Save menu item. */
   private static final String SAVE_FILE_TITLE = "Save";
   /** Text for the Quit menu item. */
   private static final String QUIT_TITLE = "Quit";
   /** Menu bar for the GUI that contains Menus. */
   private JMenuBar menuBar;
   /** Menu for the GUI. */
   private JMenu menu;
   /** Menu item for creating a new dataset of tours, clients, and reservations. */
   private JMenuItem itemNewFile;
   /** Menu item for loading a file. */
   private JMenuItem itemLoadFile;
   /** Menu item for saving the list to a file. */
   private JMenuItem itemSaveFile;
   /** Menu item for quitting the program. */
   private JMenuItem itemQuit;  
    
   // Strings for panels
   /** String to label the Available Tours panel */
   private static final String ALL_TOURS = "Available Tours";
   /** String to label the Clients panel */
   private static final String ALL_CLIENTS = "Clients";
   /** String to label the panel for reservations for the selected tour*/
   private static final String TOUR_RESERVATIONS = "Reservations for Selected Tour";
   /** String to label the filter tour panel */
   private static final String TOUR_FILTER = "Tour Display Filters";
   /** String to label the add client panel */
   private static final String ADD_CLIENT = "Add Client";
   /** String to label the new tour data panel */
   private static final String NEW_TOUR = "New Tour Data";
   /** String to label the panel for reservations for selected client */
   private static final String CLIENT_RESERVATIONS = "Reservations for Selected Client";
    
   // Buttons, RadioButtons, labels
   /** Button for canceling the selected tour (from the list of tours)*/
   private JButton btnCancelTour = new JButton("Cancel Selected Tour"); 
   /** Button for applying filters to the tour display */
   private JButton btnFilter = new JButton("Apply Filters");
   /** Button for removing all filters from the tour display (display all) */
   private JButton btnFilterOff = new JButton("Remove Filters");
   /** Button for adding a new tour to the list of available trips */
   private JButton btnAddTour = new JButton("Add New Tour");
   /** Button for canceling the selected reservation (from the client's reservations) */
   private JButton btnCancelRes = new JButton("Cancel Selected Reservation");
   /** Button for adding a new client */
   private JButton btnAddClient = new JButton("Add New Client");
   /** Button for creating a new reservation */
   private JButton btnCreateRes = new JButton("Make Reservation on Selected Tour for Selected Client");
   /** Radio buttons for filtering the trip display by kind */
   private JRadioButton[] rbtnKinds = new JRadioButton[4];
   /** Labels for the radio buttons (filtering and creating new tours) */
   private String[] rbtnLabels = {"Any", "River Cruise", "Land Tour", "Education"};
   /** Radio buttons for specifying the kind of trip to create  */
   private JRadioButton[] rbtnNewKinds = new JRadioButton[3]; 
   /** Label to display reservation costs */
   private JLabel lblCost = new JLabel("");
    
   // Lists and Tables
   /** Model for reservations for a selected trip */
   private DefaultListModel<String> dlmTourReservations = new DefaultListModel<String>();
   /** Model for reservations for list of all clients */
   private DefaultListModel<String> dlmClients = new DefaultListModel<String>();
   /** Model for reservations for a selected client */
   private DefaultListModel<String> dlmClientReservations = new DefaultListModel<String>();
   /** List of reservations for a selected trip */
   private JList<String> lstTourReservations = new JList<String>(dlmTourReservations);
   /** List of all clients */
   private JList<String> lstClients = new JList<String>(dlmClients);
   /** List of reservations for a selected client */
   private JList<String> lstClientReservations = new JList<String>(dlmClientReservations);
   /** Table for all tours (filtered) */
   private JTable tblTours;
   /** Model for all all tours (filtered) */
   private TourTableModel model;
   /** List and table font */
   private Font displayFont = new Font("Courier", Font.PLAIN, 12);
    
   // Scroll panes to hold trip/client/reservation information
   /** Scroll pane to hold tour display */
   private JScrollPane scrlPaneTour;
   /** Scroll pane to hold client display */
   private JScrollPane scrlClient;
   /** Scroll pane to hold display of reservations for selected tour */
   private JScrollPane scrlTourRes;
   /** Scroll pane to hold display of reservations for selected client */
   private JScrollPane scrlClientRes;
 
   // Filter text fields
   /** Field for smallest tour duration */
   private JTextField txtMin = new JTextField(10);
   /** Field for largest tour duration */
   private JTextField txtMax = new JTextField(10);
    
   // Trip input text fields
   /** Field for tour name */
   private JTextField txtTourName = new JTextField(10);
   /** Field for tour date */
   private JTextField txtDate = new JTextField(10);
   /** Field for tour duration */
   private JTextField txtDuration = new JTextField(10);
   /** Field for tour base price */
   private JTextField txtPrice = new JTextField(10);
   /** Field for tour capacity */
   private JTextField txtCapacity = new JTextField(10);
    
   // Client input text fields
   /** Field for client name */
   private JTextField txtClientName = new JTextField(10);
   /** Field for client contact */
   private JTextField txtClientContact = new JTextField(10);
   /** Field for party size for new reservation */
   private JTextField txtPartySize = new JTextField(10);
    
   // Main panels
   /** Panel for top left (list of tours) */
   private JPanel pnlToursLeft = new JPanel(new BorderLayout());
   /** Panel for top right (list of reservations for selected tour) */
   private JPanel pnlToursRight = new JPanel(new BorderLayout());
   /** Panel for lower left (list of clients) */
   private JPanel pnlClientsLeft = new JPanel(new BorderLayout());
   /** Panel for lower right (list of reservations for selected client) */
   private JPanel pnlClientsRight = new JPanel(new BorderLayout());
    
   /** Back end manager (provides resulting data to the GUI) */
   private TravelManager mgr = TourCoordinator.getInstance();
    
   /**
    * Main method to open the gui.
    * @param args  not used
    */
   public static void main(String[] args) {
      new TravelGUI();
   }
   
    /**
     * Constructor. Creates an empty GUI with disabled buttons.
     */
   public TravelGUI() {
      // Observe the Trip Coordinator
      ((TourCoordinator) mgr).addObserver(this);

      // Set up general GUI info
      setSize(1050, 800);
      setLocation(50, 50);
      setTitle(APP_TITLE);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setUpMenuBar();

      addWindowListener(new WindowAdapter() {
         @Override
        public void windowClosing(WindowEvent e) {
           doExit();
        }
      });

      initializeGUI();
      setVisible(true);
   }

   /**
    * Initializes GUI components, puts them together, and adds listeners.
    */
   private void initializeGUI() {
      setUpLists();
      setUpTable();
      setUpTripPanels();
      setUpClientPanels();
      putTogetherPanels(getContentPane());
      addListeners();
      enableButtons(false);
   }

   /**
    * Creates the lists and associated widgets for client and reservation displays.
    */
   private void setUpLists() {
      // Display for trip reservations 
      scrlTourRes = new JScrollPane(lstTourReservations);
      scrlTourRes.setPreferredSize(new Dimension(230, 100));
      lstTourReservations.setFont(displayFont);
      lstTourReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      // Now the display for all clients
      scrlClient = new JScrollPane(lstClients);
      scrlClient.setPreferredSize(new Dimension(230, 100));
      lstClients.setFont(displayFont);
      lstTourReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      // Display for all client reservations
      scrlClientRes  = new JScrollPane(lstClientReservations);
      scrlClientRes.setPreferredSize(new Dimension(230, 100)); 
      lstClientReservations.setFont(displayFont);
      lstClientReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   /**
    * Creates the model, table and associated widgets for trip displays.
    */
   private void setUpTable() {
      // Create the model and table and add them to a scrolling pane 
      model = new TourTableModel();
      tblTours = new JTable(model);
      scrlPaneTour = new JScrollPane(tblTours);
      tblTours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      tblTours.setPreferredScrollableViewportSize(new Dimension(230, 100));
      tblTours.setFillsViewportHeight(true);

      // Make the view pretty and readable!
      TableCellRenderer renderHeader = tblTours.getTableHeader().getDefaultRenderer();
      JLabel headerLabel = (JLabel) renderHeader;
      headerLabel.setHorizontalAlignment(JLabel.CENTER);
      DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
      rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
      for (int i = 1; i < tblTours.getColumnCount(); i++) {
         tblTours.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
      }

      Dimension tableSize = tblTours.getPreferredSize(); 
      tblTours.getColumnModel().getColumn(0).setPreferredWidth(Math.round(tableSize.width * 0.56f));
      tblTours.getColumnModel().getColumn(1).setPreferredWidth(Math.round(tableSize.width * 0.15f));
      tblTours.getColumnModel().getColumn(2).setPreferredWidth(Math.round(tableSize.width * 0.07f));
      tblTours.getColumnModel().getColumn(3).setPreferredWidth(Math.round(tableSize.width * 0.12f));
      tblTours.getColumnModel().getColumn(4).setPreferredWidth(Math.round(tableSize.width * 0.10f));
      tblTours.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      tblTours.setFont(displayFont);
   }

   /**
    * Sets up panels for all trips and reservations for selected trip.
    */
   private void setUpTripPanels() {
      // Decorate the panel borders
      pnlToursLeft.setBorder(BorderFactory.createTitledBorder(ALL_TOURS));
      pnlToursRight.setBorder(BorderFactory.createTitledBorder(TOUR_RESERVATIONS));

      // Create and populate the panel for radio button filter
      ButtonGroup group = new ButtonGroup();
      GridBagConstraints c = new GridBagConstraints();
      JPanel pnlRadioFilter = new JPanel(new GridBagLayout()); // Panel for radio buttons
      c.gridx = 0;
      c.gridy = 0;
      pnlRadioFilter.add(new JLabel("Tour Kind:  "), c);
      for (int k = 0; k < rbtnKinds.length; k++) {
         rbtnKinds[k] = new JRadioButton(rbtnLabels[k]);
         rbtnKinds[k].addActionListener(this);
         group.add(rbtnKinds[k]);
         c.gridx += 1;
         pnlRadioFilter.add(rbtnKinds[k], c);
      }
      rbtnKinds[0].setSelected(true); 

      // Create the panel for minimum and maximum filters choices
      JPanel pnlMinMax = new JPanel(new SpringLayout()); 
      pnlMinMax.add(new JLabel("Min Days:"));
      pnlMinMax.add(txtMin);
      pnlMinMax.add(new JLabel("Max Days:"));
      pnlMinMax.add(txtMax);
      // NEW NEW
      pnlMinMax.add(btnFilter);
      pnlMinMax.add(btnFilterOff);
      SpringUtilities.makeCompactGrid(pnlMinMax, 1, 6, 6, 6, 6, 6);   

      // Create a panel for all filter components. Add radio and min/max filter components
      JPanel pnlFilter = new JPanel();  // Panel to hold filter components
      pnlFilter.setLayout(new BoxLayout(pnlFilter, BoxLayout.Y_AXIS));
      pnlFilter.setBorder(BorderFactory.createTitledBorder(TOUR_FILTER));
      pnlFilter.add(pnlRadioFilter);
      pnlFilter.add(pnlMinMax);

      // Create the panel for listing all trips
      JPanel pnlListTrips = new JPanel(new BorderLayout());
      pnlListTrips.add(scrlPaneTour, BorderLayout.CENTER);
      pnlListTrips.add(btnCancelTour, BorderLayout.SOUTH);

      // Add the panels for listing all trips and for filtering to the upper left panel
      pnlToursLeft.add(pnlListTrips, BorderLayout.CENTER);
      pnlToursLeft.add(pnlFilter, BorderLayout.SOUTH);

      // Right side now -- scrolling list for reservations PLUS new Trip
      JPanel pnlListTripRes = new JPanel(new BorderLayout());
      pnlListTripRes.add(scrlTourRes, BorderLayout.CENTER);

      // Create the panel for new trips
      JPanel pnlTripData = new JPanel();
      pnlTripData.setLayout(new BorderLayout());
      pnlTripData.setBorder(BorderFactory.createTitledBorder(NEW_TOUR));

      // Create another panel for choosing the type of new trip
      JPanel pnlRadioNewTrip = new JPanel(new GridBagLayout());
      ButtonGroup groupNewTrip = new ButtonGroup();
      c.gridx = 0;
      pnlRadioNewTrip.add(new JLabel("Tour Kind:  "), c);
      for (int k = 0; k < rbtnNewKinds.length; k++) {
         c.gridx += 1;
         rbtnNewKinds[k] = new JRadioButton(rbtnLabels[k + 1]);
         rbtnNewKinds[k].addActionListener(this);
         pnlRadioNewTrip.add(rbtnNewKinds[k], c);
         groupNewTrip.add(rbtnNewKinds[k]);
      }
      rbtnNewKinds[0].setSelected(true);
      pnlTripData.add(pnlRadioNewTrip, BorderLayout.NORTH);

      // Create and populate the panel for new trip data and a button to add the trip
      JPanel pnlUserData = new JPanel(new SpringLayout()); // User text data panel
      pnlUserData.add(new JLabel("Name:"));
      pnlUserData.add(txtTourName);
      pnlUserData.add(new JLabel("Start Date:"));
      pnlUserData.add(txtDate);
      pnlUserData.add(new JLabel("Duration:"));
      pnlUserData.add(txtDuration);
      pnlUserData.add(new JLabel("Base Price: $"));
      pnlUserData.add(txtPrice);
      pnlUserData.add(new JLabel("Capacity:"));
      pnlUserData.add(txtCapacity);
      SpringUtilities.makeCompactGrid(pnlUserData, 5, 2, 6, 0, 6, 2); 
      pnlTripData.add(pnlUserData, BorderLayout.CENTER);       
      pnlTripData.add(btnAddTour, BorderLayout.SOUTH);

      // Add panels for listing reservations and creating new trips to right trip panel
      pnlToursRight.add(pnlListTripRes, BorderLayout.CENTER);
      pnlToursRight.add(pnlTripData, BorderLayout.SOUTH);  
   }


   /** 
    * Puts all panels together in new GridLayout.
    * @param cMain  Container to hold the panels
    */
   private void putTogetherPanels(Container cMain) {
      cMain.setLayout(new GridLayout(2, 2));
      cMain.add(pnlToursLeft);
      cMain.add(pnlToursRight);
      cMain.add(pnlClientsLeft);
      cMain.add(pnlClientsRight);   
   }

   /** 
    * Adds listeners to buttons and lists of trips and clients.
    */
   private void addListeners() {
      // Add button listeners
      btnAddTour.addActionListener(this);
      btnAddClient.addActionListener(this);
      btnCancelRes.addActionListener(this);
      btnCancelTour.addActionListener(this);
      btnCreateRes.addActionListener(this);
      btnFilter.addActionListener(this);
      btnFilterOff.addActionListener(this);

      // Add listeners for list of all trips (table rows) and list of all clients
      tblTours.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting()) {
               refreshReservationsForSelectedTour();
            }
         }
      });
      lstClients.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting()) {
               refreshReservationsForSelectedClient();
            }
         }
      });      
   }

   /**
    * Makes the GUI Menu bar that contains options for loading a file, starting/opening
    * a new file, saving data to a file, and quitting the application.
    */
   private void setUpMenuBar() {
      // Construct Menu items
      menuBar = new JMenuBar();
      menu = new JMenu(FILE_MENU_TITLE);
      itemNewFile = new JMenuItem(NEW_FILE_TITLE);
      itemLoadFile = new JMenuItem(LOAD_FILE_TITLE);
      itemSaveFile = new JMenuItem(SAVE_FILE_TITLE);
      itemQuit = new JMenuItem(QUIT_TITLE);
      itemNewFile.addActionListener(this);
      itemLoadFile.addActionListener(this);
      itemSaveFile.addActionListener(this);
      itemQuit.addActionListener(this);

      // Start with save item disabled
      itemSaveFile.setEnabled(false);

      // Build Menu and add to GUI
      menu.add(itemNewFile);
      menu.add(itemLoadFile);
      menu.add(itemSaveFile);
      menu.add(itemQuit);
      menuBar.add(menu);
      this.setJMenuBar(menuBar);
   }

   /**
    * Creates the client panels and put them together.
    */
   private void setUpClientPanels() {
      // Set layout, borders, and scroll panes on the left and right side panels
      pnlClientsLeft.setLayout(new BorderLayout());
      pnlClientsRight.setLayout(new BorderLayout());
      pnlClientsLeft.setBorder(BorderFactory.createTitledBorder(ALL_CLIENTS));
      pnlClientsRight.setBorder(BorderFactory.createTitledBorder(CLIENT_RESERVATIONS));
      pnlClientsLeft.add(scrlClient, BorderLayout.CENTER);

      // Create the panel and widgets for adding a new client
      JPanel pnlAddNewClient = new JPanel(new BorderLayout());
      pnlAddNewClient.setBorder(BorderFactory.createTitledBorder(ADD_CLIENT));
      JPanel pnlNewClientData = new JPanel(new SpringLayout());
      pnlNewClientData.add(new JLabel("Client Name"));
      pnlNewClientData.add(txtClientName);
      pnlNewClientData.add(new JLabel("Client Contact"));
      pnlNewClientData.add(txtClientContact);
      SpringUtilities.makeCompactGrid(pnlNewClientData, 2, 2, 6, 0, 6, 2);
      pnlAddNewClient.add(pnlNewClientData, BorderLayout.CENTER);
      pnlAddNewClient.add(btnAddClient, BorderLayout.SOUTH);
      pnlClientsLeft.add(pnlAddNewClient, BorderLayout.SOUTH);

      // Create the new panels and widgets for adding/canceling reservations
      JPanel pnlShowReservations = new JPanel(new BorderLayout());
      pnlShowReservations.add(scrlClientRes, BorderLayout.CENTER);
      lblCost.setHorizontalAlignment(SwingConstants.RIGHT);
      pnlShowReservations.add(lblCost, BorderLayout.SOUTH);
      pnlClientsRight.add(pnlShowReservations, BorderLayout.CENTER);
      JPanel pnlMakeChangeReservations = new JPanel(new BorderLayout());
      pnlMakeChangeReservations.setBorder(BorderFactory.createTitledBorder("Make/Change Reservations"));
      pnlMakeChangeReservations.add(btnCancelRes, BorderLayout.NORTH);
      pnlMakeChangeReservations.add(btnCreateRes, BorderLayout.SOUTH);
      JPanel pnlPartySize = new JPanel();
      pnlPartySize.add(new JLabel("Party size for new reservation: "));
      pnlPartySize.add(txtPartySize);
      pnlMakeChangeReservations.add(pnlPartySize, BorderLayout.CENTER);
      pnlClientsRight.add(pnlMakeChangeReservations, BorderLayout.SOUTH);
   }

   /**
    * Enable/disable buttons.
    * @param value If true, enable buttons. If false, disable buttons.
    */
   private void enableButtons(boolean value) {
      btnAddClient.setEnabled(value);
      btnAddTour.setEnabled(value);
      btnCancelTour.setEnabled(value);
      btnCancelRes.setEnabled(value);
      btnCreateRes.setEnabled(value);
      btnFilter.setEnabled(value);
      btnFilterOff.setEnabled(value);
   }

   /** 
    * Updates the GUI in response to the TourCoordinator.
    * @param  o  the TourCoordinator
    * @param  arg  the GUI (this)
    */
   @Override
   public void update(Observable o, Object arg) {
      if (o instanceof TourCoordinator) {
         itemSaveFile.setEnabled(true);
         itemNewFile.setEnabled(true);
         this.refreshAllClients();
         this.refreshAllTrips();
         refreshReservationsForSelectedClient();
         refreshReservationsForSelectedTour();            
      }     
   }

   /**
    * Performs the task corresponding to the user's action.
    * @param  e The event that occurred (the user's action)
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      try {
         if (e.getSource() == itemNewFile) {
            doNewFile();
         } else if (e.getSource() == itemLoadFile) {
            doLoadFile();
         } else if (e.getSource() == itemSaveFile) {
            doSaveFile();
         } else if (e.getSource() == itemQuit) {
            doExit();
         }
         if (e.getSource().equals(btnCancelTour))  // Cancel selected trip
            doCancelTour();
         if (e.getSource().equals(btnFilter)) { // Cancel selected client's selected reservation
            String kind = "A";
            for (int k = 1; k < 4; k++)
               if (rbtnKinds[k].isSelected())
                  kind = rbtnLabels[k];
            doFilterTours(kind, txtMin.getText(), txtMax.getText());
         }
         if (e.getSource().equals(btnFilterOff)) {
            doFilterTours("A", "1", "Integer.MAX_VALUE");
            rbtnKinds[0].setSelected(true);
            txtMax.setText("");
            txtMin.setText("");
         }
         if (e.getSource().equals(btnAddClient)) {
            doAddClient(); 
         }
         if (e.getSource().equals(btnAddTour)) 
            doAddTour();
         if (e.getSource().equals(btnCancelRes)) { // Cancel selected client's selected reservation
            doCancelReservation();
         }
         if (e.getSource().equals(btnCreateRes)) 
            doCreateReservation(); 
      } catch (IllegalArgumentException exc) {
         JOptionPane.showMessageDialog(this, exc.getMessage(),
               "Data Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   /**
    * Handles canceling a trip.
    * @throws IllegalArgumentException if no trip is selected
    */
   private void doCancelTour() {
      int index = tblTours.getSelectedRow();
      if (index > -1)  { // A trip was selected
         mgr.cancelTour(index);
      }
      else
         throw new IllegalArgumentException("No tour selected");
   }

   /**
    * Handles adding a new tour.
    * @throws IllegalArgumentException if tour cannot be made with the input data
    */
   private void doAddTour() {
      String kind = "";
      for (int i = 0; i < 3; i++)   {
         if (rbtnNewKinds[i].isSelected())
            kind = rbtnLabels[i + 1];
      }
      LocalDate theDate = null;
      try {
         String[] date = txtDate.getText().split("/");
         int year = Integer.parseInt(date[2]);
         if (year < 2000)
            year += 2000;
         theDate = LocalDate.of(year, Integer.parseInt(date[0]), Integer.parseInt(date[1]));
      } catch (Exception e) {
         throw new IllegalArgumentException("Date must be in the form mm/dd/yy");
      }
      try {
         mgr.addNewTour(kind, txtTourName.getText(), theDate,
               Integer.parseInt(txtDuration.getText()), 
               Integer.parseInt(txtPrice.getText()), 
               Integer.parseInt(txtCapacity.getText()));
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Duration, Base Price, and Capacity must be whole numbers.");
      } catch (DuplicateTourException e) {
         throw new IllegalArgumentException("Cannot add a tour that duplicates an existing one.");
      }
      txtTourName.setText("");
      txtDate.setText("");
      txtDuration.setText("");
      txtPrice.setText("");
      txtCapacity.setText("");
   }

   /**
    * Filters the tour display based on the filter data. If max/min duration is illegal, 
    * sets to 1/Integer.MAX_VALUE and clears corresponding text field.
    * @param kind  Kind of tour (River Cruise, Land Tour, Educational)
    * @param min  Minimum days duration
    * @param max  Maximum days duration
    */
   private void doFilterTours(String kind, String min, String max) {
      // Take care of max/min
      int minVal = 1;
      int maxVal = Integer.MAX_VALUE;
      try {
         minVal = Integer.parseInt(min);
      }
      catch (Exception eMin) {
         txtMin.setText("");
      }
      try {
         maxVal = Integer.parseInt(max);
      }
      catch (Exception eMax) {
         txtMax.setText("");
      }
      mgr.setFilters(kind, minVal, maxVal);
      refreshAllTrips();
   }

   /**
    * Adds a new client based on user data.
    * @throws an IllegalArgumentException if any data are invalid
    */
   private void doAddClient() {
      String name = txtClientName.getText();
      String contact = txtClientContact.getText();
      try {
         mgr.addNewClient(name, contact);
         txtClientName.setText("");
         txtClientContact.setText("");
      }
      catch (DuplicateClientException | IllegalArgumentException e) {
         throw new IllegalArgumentException(e.getMessage());
      }
   }

   /**
    * Creates a new reservation for the selected client and trip. 
    * @throws IllegalArgumentException if the capacity is exceeded or selections are not valid
    */
   private void doCreateReservation() {
      int clientIndex = lstClients.getSelectedIndex();
      int tripIndex = tblTours.getSelectedRow();
      if (clientIndex < 0 || tripIndex < 0)
         throw new IllegalArgumentException("Select a client and trip for the new reservation.");
      try {
         int num = Integer.parseInt(txtPartySize.getText());
         mgr.addNewReservation(clientIndex, tripIndex, num);
         txtPartySize.setText("");
      } catch (NumberFormatException e) {
         txtPartySize.setText("");
         throw new IllegalArgumentException("Number in party must be a whole number.");
      } catch (CapacityException e) {
         txtPartySize.setText("");
         throw new IllegalArgumentException("Not enough space in selected tour for this party.");
      }
      lstClients.setSelectedIndex(clientIndex);
      tblTours.setRowSelectionInterval(tripIndex, tripIndex);
   }

   /**
    * Cancels the selected reservations for the selected client.
    * @throws IllegalArgumentException if the selections are not valid
    */
   private void doCancelReservation() {
      int clientIndex = lstClients.getSelectedIndex();
      int tripIndex = tblTours.getSelectedRow();
      int resIndex = lstClientReservations.getSelectedIndex();
      if (clientIndex < 0 || resIndex < 0)
         throw new IllegalArgumentException("Select a client then the client's reservation to cancel.");
      mgr.cancelReservation(clientIndex, resIndex);
      lstClients.setSelectedIndex(clientIndex);
      if (tripIndex >= 0)
         tblTours.setRowSelectionInterval(tripIndex, tripIndex); 
   }

   /**
    * Refreshes the trip displays, clears the selection and the corresponding trip reservations.
    */
   private void refreshAllTrips() {
      tblTours.clearSelection();
      model.updateTripData();
      // Now clear the associated trip reservation display
      lstTourReservations.clearSelection();
      dlmTourReservations.clear();
   }

   /**
    * Refreshes list of reservations for the selected tour. Changes reservation display.
    */
   private void refreshReservationsForSelectedTour() {
      dlmTourReservations.clear();
      int whichTrip = tblTours.getSelectedRow();
      if (whichTrip >= 0) {
         String[] tripRes = mgr.reservationsForATour(whichTrip);
         for (String c : tripRes) {
            dlmTourReservations.addElement(c);
         }
      }
   }
   /**
    * Refreshes the list of all clients. Changes client and associated reservation display.
    */
   private void refreshAllClients() {
      lstClients.clearSelection();
      dlmClients.clear();
      String[] theClients = mgr.listClients();
      for (String s : theClients)
         dlmClients.addElement(s);
      // Now clear the associated client reservation display
      lstClientReservations.clearSelection();
      dlmClientReservations.clear();
   }

   /**
    * Refreshes the list of reservations for the selected client. Changes reservation display.
    */
   private void refreshReservationsForSelectedClient() {
      dlmClientReservations.clear();
      int whichClient = lstClients.getSelectedIndex();
      if (whichClient >= 0) {
         String[] clientRes = mgr.reservationsForAClient(whichClient);
         for (String c : clientRes) {
            dlmClientReservations.addElement(c);
         }
         double resCost = mgr.totalClientCost(whichClient);
         if (resCost > 0) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            lblCost.setText("Total cost of reservations: " + formatter.format(resCost));
         } else
            lblCost.setText("");
      }
      else
         lblCost.setText("");    
   }

   /**
    * Exits the GUI. First queries user on whether to save data if there were changes.
    */
   private void doExit() {
      if (((TourCoordinator) mgr).dataShouldBeSaved())
         saveBeforeErasing();
      System.exit(NORMAL);
   }

   /**
    * Displays a dialog to determine whether the current dataset should be saved.
    */
   private void saveBeforeErasing() {
      int save = JOptionPane.showConfirmDialog(this,
            "Tour/client/reservation data have not been saved. Do you want to save them?",
            "Save or Discard Changes",
            JOptionPane.YES_NO_OPTION);
      if (save == JOptionPane.YES_OPTION) {
         doSaveFile();
      }
   }

   /**
    * Saves a file if any changes occurred to back end. Then flushes the data.
    */
   private void doNewFile() {
      if (((TourCoordinator) mgr).dataShouldBeSaved())
         saveBeforeErasing();
      ((TourCoordinator) mgr).flushLists();
      enableButtons(true);
   }

   /**
    * Loads back end data from a file.
    */
   private void doLoadFile() {
      if (((TourCoordinator) mgr).dataShouldBeSaved())
         saveBeforeErasing();
      try {
         JFileChooser chooser = new JFileChooser("./");
         FileNameExtensionFilter filter = new FileNameExtensionFilter(
               "Travel Data Files (md)", "md");
         chooser.setFileFilter(filter);
         chooser.setMultiSelectionEnabled(false);
         int returnVal = chooser.showOpenDialog(this);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
            mgr.loadFile(chooser.getSelectedFile().getAbsolutePath()); 
         }
         enableButtons(true);
      } catch (IllegalArgumentException e) {
         JOptionPane.showMessageDialog(this, "Error opening file.",
               "Opening Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   /**
    * Saves back end data to a file.
    */
   private void doSaveFile() {
      try {
         JFileChooser chooser = new JFileChooser("./");
         FileNameExtensionFilter filter = new FileNameExtensionFilter(
               "Tour data files (md)", "md");
         chooser.setFileFilter(filter);
         chooser.setMultiSelectionEnabled(false);
         if (mgr.getFilename() != null) {
            chooser.setSelectedFile(new File(mgr.getFilename()));
         }
         int returnVal = chooser.showSaveDialog(this);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            if (chooser.getSelectedFile().getName().trim().equals("")
                  || !chooser.getSelectedFile().getName()
                  .endsWith(".md")) {
               throw new IllegalArgumentException();
            }
            mgr.saveFile(filename);
         }
         else
            JOptionPane.showMessageDialog(this, "Data not saved.",
                  "Saving Error", JOptionPane.ERROR_MESSAGE);
         itemLoadFile.setEnabled(true);
         itemNewFile.setEnabled(true);
         itemSaveFile.setEnabled(false);
      } catch (IllegalArgumentException e) {
         JOptionPane.showMessageDialog(this, "File not saved.",
               "Saving Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   /**
    * Defines a TableModel for all trip data.
    * Reference for custom renderer: 
    *    https://kodejava.org/how-do-i-right-align-numbers-data-in-jtable-component/
    * @author Jo Perry
    */
   private class TourTableModel extends AbstractTableModel {
      /** Column names for the table */
      private String[] columns = {"Kind-Tour Name", "Start Date", "Days", "Price", "Cap"};
      /** Data stored in the table */
      private Object[][] data = null; 

      /**
       * Constructor simply calls the super constructor.
       */
      public TourTableModel() {
         super();          
      }

      @Override
      public int getColumnCount() {
         return columns.length;
      }

      @Override
      public String getColumnName(int column) {
         return columns[column];
      }

      @Override
      public int getRowCount() {
         if (data == null)
            return 0;
         return data.length;
      }

      /**
       * Updates the trip data according to the TripCoordinator
       */
   public void updateTripData() {
         data = mgr.filteredTourData();
         TravelGUI.this.repaint();
         TravelGUI.this.validate();
         scrlPaneTour.repaint();
         scrlPaneTour.validate();
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
         if (data == null)
            return null;
         return data[rowIndex][columnIndex];
      }

      /**
       * This method defines the default renderer or editor for each cell. So 
       * boolean data are rendered as check boxes, numbers are right aligned.
       */
      @Override
      public Class<?> getColumnClass(int columnIndex) {
         return data[0][columnIndex].getClass();
      }      
   }

}