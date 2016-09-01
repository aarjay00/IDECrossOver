import plotly.plotly as py
from plotly.graph_objs import Scatter
import plotly

def set_credentials():
    
#   Set credentials for online plotting  
    plotly.tools.set_credentials_file(username='aarjay00', api_key='89q89hwig4')

def basic_scatter_plot_online(xy,graph_name):
    set_credentials()
    x=[i[0] for i in xy ]
    y=[i[1] for i in xy]
    trace=Scatter(x=x,y=y,mode='markers')
    plotly.plotly.plot([trace],filename=graph_name)


def basic_scatter_plot_offline(xy,graph_name):
    set_credentials()
    x=[i[0] for i in xy ]
    y=[i[1] for i in xy]
    
    marker=dict(color='red',size='50',symbol='square-dot')
    trace=Scatter(x=x,y=y,mode='markers',name='col_name',marker=marker,connectgaps=True)
    plotly.offline.iplot([trace],filename=graph_name)

    
dev_1 = ['U','U','E','D','D','D','U','X']
dev_2 = ['U','U','U','D','D','U']
activity_color_map={'U':'blue','E':'green','D':'red'}

def create_trace_from_dev_activity(dev_activity,dev_name):
        
        trace_x_understand = [idx for idx,activity in enumerate(dev_activity) if activity=='U']
        trace_x_edit = [idx for idx,activity in enumerate(dev_activity) if activity=='E']
        trace_x_debug= [idx for idx,activity in enumerate(dev_activity) if activity=='D']
        trace_x_none  = [idx for idx,activity in enumerate(dev_activity) if activity=='X']
    
        marker_understand =dict(color='blue',size='15',symbol='square-dot')
        marker_edit =dict(color='green',size='15',symbol='square-dot')
        marker_debug =dict(color='red',size='15',symbol='square-dot')
        marker_none = dict(color='black',size='15',symbol='square-dot')
    
        trace_understand = Scatter(x=trace_x_understand,y=[dev_name]*len(trace_x_understand)
                                   ,marker=marker_understand
                                   ,hoverinfo='none'
                                   ,mode='markers'
                                   ,showlegend=False
                                  ,name='understand_actions')
        
        trave_edit = Scatter(x=trace_x_edit,y=[dev_name]*len(trace_x_edit)
                             ,marker=marker_edit
                             ,hoverinfo='none'
                             ,mode='markers'
                             ,showlegend=False
                            ,name='edit_actions')

        
        trace_debug = Scatter(x=trace_x_debug,y=[dev_name]*len(trace_x_debug)
                              ,marker=marker_debug,
                              hoverinfo='none',
                              mode='markers'
                              ,showlegend=False
                             ,name='debug_actions')

        trace_none = Scatter(x=trace_x_none,y=[dev_name]*len(trace_x_none)
                              ,marker=marker_none,
                              hoverinfo='none',
                              mode='markers'
                              ,showlegend=False
                             ,name='none_actions')

        return [trace_understand,trave_edit,trace_debug,trace_none]
    
def create_dev_activity_graph(dev_activity,dev_name):
#     plotly.offline.init_notebook_mode()
    set_credentials()
    trace_collection=[]    
    trace_collection.extend(create_trace_from_dev_activity(dev_activity,dev_name))
#     plotly.plotly.image.save_as(trace_collection,dev_name+'.png')
    plotly.plotly.plot(trace_collection,dev_name.split('/')[-1])
#     plotly.offline.iplot(trace_collection)
# trace_collection.extend(create_trace_from_dev_activity(dev_2,'dev_2'))

        
# basic_scatter_plot_offline([[1,'b'],[2,'a']],'trial')