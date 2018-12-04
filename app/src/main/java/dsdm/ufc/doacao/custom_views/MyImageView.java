package dsdm.ufc.doacao.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {

    private float strokeLineWidth;
    private float strokeButtonWidth;
    private int colorLine;
    private int numberOfDashes;
    private int colorAddButton;

    public MyImageView(Context context) {
        super(context);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        strokeLineWidth = 10.0F;
        colorLine = Color.MAGENTA;
        numberOfDashes = 15;
        colorAddButton = Color.GRAY;
        strokeButtonWidth = 25.0F;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        if( this.getDrawable() != null )
            return;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        Paint linePaint = new Paint();

        linePaint.setColor(colorLine);
        linePaint.setStrokeWidth(strokeLineWidth);

        float sizePieces = numberOfDashes;
        float step = strokeLineWidth / 2;

        float sX = contentWidth / sizePieces;
        float sY = contentHeight / sizePieces;

        canvas.translate(paddingLeft, paddingTop);

        for(int i = 0; i < 8; ++i)
            canvas.drawLine( 2*i*sX, step, 2*i*sX+sX, step, linePaint);
        for(int i = 0; i < 8; ++i)
            canvas.drawLine( 2*i*sX, contentHeight-step, 2*i*sX+sX, contentHeight-step, linePaint);
        for(int i = 0; i < 8; ++i)
            canvas.drawLine( step, 2*i*sY, step, 2*i*sY+sY, linePaint);
        for(int i = 0; i < 8; ++i)
            canvas.drawLine( contentWidth-step, 2*i*sY, contentWidth-step, 2*i*sY+sY, linePaint);

        Paint addPaint = new Paint();

        addPaint.setColor(colorAddButton);
        addPaint.setStrokeWidth(strokeButtonWidth);

        float halfWidth = contentWidth / 2;
        float halfHeight = contentHeight / 2;
        float addHalfSize = contentWidth / 7;

        canvas.drawLine(halfWidth-addHalfSize, halfHeight,
                halfWidth + addHalfSize, halfHeight, addPaint);
        canvas.drawLine(halfWidth, halfHeight-addHalfSize,
                halfWidth, halfHeight+addHalfSize, addPaint);
    }

    public float getStrokeLineWidth() {
        return strokeLineWidth;
    }

    public void setStrokeLineWidth(float strokeLineWidth) {
        this.strokeLineWidth = strokeLineWidth;
    }

    public float getStrokeButtonWidth() {
        return strokeButtonWidth;
    }

    public void setStrokeButtonWidth(float strokeButtonWidth) {
        this.strokeButtonWidth = strokeButtonWidth;
    }

    public int getColorLine() {
        return colorLine;
    }

    public void setColorLine(int colorLine) {
        this.colorLine = colorLine;
    }

    public int getNumberOfDashes() {
        return numberOfDashes;
    }

    public void setNumberOfDashes(int numberOfDashes) {
        this.numberOfDashes = numberOfDashes;
    }

    public int getColorAddButton() {
        return colorAddButton;
    }

    public void setColorAddButton(int colorAddButton) {
        this.colorAddButton = colorAddButton;
    }
}
