package ru.redactor.patterns.grand.behavioral.concurrency.asynchronousprocessing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class CancelFrame extends JFrame {

    JMenuItem menuHelpAbout = new JMenuItem();
    JMenu menuHelp = new JMenu();
    JMenuItem menuFileDoIt = new JMenuItem();
    JMenuItem menuFileStopDoingIt = new JMenuItem();
    JMenuItem menuFileExit = new JMenuItem();
    JMenu menuFile = new JMenu();
    JMenuBar menuBarl = new JMenuBar();
    BorderLayout borderLayoutl = new BorderLayout();
    JProgressBar myProgress = new JProgressBar();

    Thread doItThread;

    public CancelFrame() {
        try {
            jbInit();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Инициализирует содержимое этого фрейма .
     */
    private void jbInit() throws Exception {

        this.setJMenuBar(menuBarl);
        this.getContentPane().setLayout(borderLayoutl);
        this.setSize(new Dimension(400, 300));
        this.setTitle("Cancel Example Application");

        menuFile.setText("File");
        menuFileDoIt.setText("Do It");

        menuFileDoIt.addActionListener(ae -> fileDoIt_ActionPerformed(ae));

        menuFileStopDoingIt.setText("Stop Doing It");
        menuFileStopDoingIt.addActionListener(ae -> fileStopDoingIt_ActionPerformed(ae));

        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(ae -> fileExit_ActionPerformed(ae));

        menuHelp.setText("Help");

        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(ae -> helpAbout_ActionPerformed(ae));

        menuFile.add(menuFileDoIt);
        menuFile.add(menuFileExit);
        menuBarl.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBarl.add(menuHelp);
        this.getContentPane().add(myProgress, BorderLayout.SOUTH);

    }

    /**
     * Асинхронно устанавливает индикатор выполнения в ноль, а затем постепенно, в течение нескольких секунд
     * изменяет его показание до 100 процентов
     */
    void fileDoIt_ActionPerformed(ActionEvent e) {
        doItThread = new Thread() {
            public void run() {
                try {
                    installStopItMenuItem();
                    myProgress.setMinimum(0);
                    myProgress.setMaximum(19);
                    myProgress.setValue(0);
                    myProgress.repaint();
                    for (int i = 0; i < 20; i++) {
                        Thread.currentThread().sleep(300);
                        myProgress.setValue(i);
                        myProgress.repaint();
                    }
                } catch (InterruptedException exc) {
                } finally {
                    installDoItMenuItem();
                    doItThread = null;
                }
            }
        };
        doItThread.start();
    }

    private void installStopItMenuItem() {
        menuFile.remove(menuFileDoIt);
        menuFile.insert(menuFileStopDoingIt, 0);
        menuFile.repaint();
    }

    private void installDoItMenuItem() {
        menuFile.remove(menuFileStopDoingIt);
        menuFile.insert(menuFileDoIt, 0);
        menuFile.repaint();
    }

    void fileStopDoingIt_ActionPerformed(ActionEvent e) {
        if (doItThread != null) {
            doItThread.interrupt();
        }
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JPanel aboutPanel = new JPanel();
        JOptionPane.showMessageDialog(this, aboutPanel, "About", JOptionPane.PLAIN_MESSAGE);
    }

}
