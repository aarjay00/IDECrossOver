import plotly.plotly as py
import plotly.graph_objs as go
import plotly

def set_credentials():
    plotly.tools.set_credentials_file(username='aarjay00', api_key='89q89hwig4')


def basic_two_plot(xy,graph_name):

    set_credentials()
    x=[i[0] for i in xy ]
    y=[i[1] for i in xy]
    trace=go.Scatter(x=x,y=y,mode='markers')
    py.plot([trace],filename=graph_name)
