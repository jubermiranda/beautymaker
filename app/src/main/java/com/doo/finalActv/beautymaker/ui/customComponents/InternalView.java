package com.doo.finalActv.beautymaker.ui.customComponents;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;


public class InternalView extends JInternalFrame{
private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public InternalView(int width, int height) {
        setSize(width, height);
        setupFrameAppearance();
    }

    public InternalView() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void setupFrameAppearance() {
        putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        setRootPaneCheckingEnabled(false);

        if (getUI() instanceof BasicInternalFrameUI basicUI) {
            basicUI.setNorthPane(null);
        }
        setResizable(false);
        setOpaque(false);
        getRootPane().setOpaque(false);
        ((JComponent) getContentPane()).setOpaque(false);
        setBorder(null);
    }
}
