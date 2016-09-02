import bisect


def hotkey_analysis(command_list):
    eclipse_command_list = filter(lambda x: x['command_type'] == "EclipseCommand", command_list)

    shortcut_list = filter(lambda x: "styledTextCommand" not in x['@commandID'] and x['@commandID'] != "keyDownEvent",eclipse_command_list)


    # print set(shortcut_list)

    keydownevent_list = list(filter(lambda x: x['@commandID'] == 'keyDownEvent', eclipse_command_list))
    keydownevent_list_num = [int(keydownevent['log_num']) for keydownevent in keydownevent_list]
    mouse_movement_list = filter(lambda x: x['command_type'] == 'MoveCaretCommand', command_list)

    shortcut_usage={}

    for shortcut in shortcut_list:

        try:
            l=keydownevent_list_num[bisect.bisect_left(keydownevent_list_num,int(shortcut['log_num']))]
        except:
            continue
        r=keydownevent_list_num[bisect.bisect_right(keydownevent_list_num,int(shortcut['log_num']))]
        m=int(shortcut['log_num'])
        print shortcut['@commandID'],l,m,r

        if(shortcut['@commandID'] not in shortcut_usage):
            shortcut_usage[shortcut['@commandID']]=[0,0]
        if abs(l-m)<=2 or abs(r-m)<=2 :
            shortcut_usage[shortcut['@commandID']][0]+=1
        else:
            shortcut_usage[shortcut['@commandID']][1]+=1

    for shortcut in shortcut_usage.keys():
        print shortcut,shortcut_usage[shortcut]